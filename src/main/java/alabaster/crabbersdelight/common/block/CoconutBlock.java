package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.registry.CDDamageSources;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class CoconutBlock extends FallingBlock {
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);

    public CoconutBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HANGING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HANGING)) {
            return Block.box(3.0D, 5.0D, 3.0D, 13.0D, 15.0D, 13.0D);
        } else {
            return SHAPE;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (facing == Direction.UP) {
            boolean hanging = !neighborState.isAir();
            if (!hanging) {
                level.scheduleTick(pos, this, 1);
            }
            return state.setValue(HANGING, hanging);
        }
        return super.updateShape(state, facing, neighborState, level, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos above = context.getClickedPos().above();
        BlockState aboveState = level.getBlockState(above);
        boolean hanging = !aboveState.isAir();
        return this.defaultBlockState().setValue(HANGING, hanging);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean hanging = state.getValue(HANGING);
        if (!hanging) {
            super.tick(state, level, pos, random);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // no particles
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceState, FallingBlockEntity fallingEntity) {
        super.onLand(level, pos, state, replaceState, fallingEntity);

        if (!level.isClientSide && fallingEntity != null) {
            boolean hitPlayer = false;
            List<Entity> entities = level.getEntities(null, SHAPE.bounds().move(pos));

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity living) {
                    living.hurt(CDDamageSources.getSimpleDamageSource(level, CDDamageSources.FALLING_COCONUT), 2.0F);

                    if (entity instanceof Player player) {
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.GOAT_HORN_BREAK, SoundSource.PLAYERS, 0.7F, 0.5F);

                        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));

                        ItemStack headSlot = player.getItemBySlot(EquipmentSlot.HEAD);
                        if (headSlot.isEmpty()) {
                            ItemStack coconutHelmet = new ItemStack(CDModItems.COCONUT_HELMET.get());
                            player.setItemSlot(EquipmentSlot.HEAD, coconutHelmet);
                            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1.0F, 1.0F);
                            level.destroyBlock(pos, false);
                        }

                        hitPlayer = true;
                    }
                }
            }

            if (hitPlayer) {
                level.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        super.onProjectileHit(level, state, hit, projectile);

        if (!level.isClientSide) {
            BlockPos pos = hit.getBlockPos();
            if (state.getValue(HANGING)) {
                level.setBlock(pos, state.setValue(HANGING, false), Block.UPDATE_ALL);
                level.scheduleTick(pos, this, 1);
            }
        }
    }
}
