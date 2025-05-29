package alabaster.crabbersdelight.common.entity.crab;

import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class CrabEntity extends Animal implements Bucketable {
    private static EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    private Ingredient temptationItems;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);

    public CrabEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.75, getTemptationItems(), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1);
    }

    @Override
    public int getMaxAirSupply() {
        return 6000;
    }

    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterRainOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().dryOut(), 2.0F);
            }
        } else {
            this.setAirSupply(this.getMaxAirSupply());
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (!this.isNoAi()) {
            this.handleAirSupply(i);
        }
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        Holder<Biome> holder = level.getBiome(this.blockPosition());
        if (holder.is(Biomes.MANGROVE_SWAMP)) {
            this.setVariant(CrabVariant.BLUE);
        } else if (holder.is(Biomes.BEACH)) {
            this.setVariant(CrabVariant.RED);
        } else if (holder.is(Biomes.SWAMP)) {
            this.setVariant(CrabVariant.GREEN);
        } else if (holder.is(Biomes.STONY_SHORE)) {
            this.setVariant(CrabVariant.LIGHT_GRAY);
        } else if (holder.is(Biomes.SNOWY_BEACH)) {
            this.setVariant(CrabVariant.WHITE);
        } else {
            this.setVariant(CrabVariant.BLUE);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static boolean checkCrabSpawnRules(EntityType<CrabEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockState stateBelow = level.getBlockState(pos.below());
        return stateBelow.is(CDModTags.CRAB_SPAWN_ON);
    }

    public static boolean canCrabSpawn(EntityType<CrabEntity> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(CDModTags.CRAB_SPAWN_ON);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(FROM_BUCKET, false);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public CrabVariant getVariant() {
        return CrabVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(CrabVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getTypeVariant());
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        this.entityData.set(FROM_BUCKET, compound.getBoolean("FromBucket"));
    }

    @Override
    @Nullable
    public CrabEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        CrabEntity baby = CDModEntities.CRAB.get().create(level);
        if (!(otherParent instanceof CrabEntity otherCrab)) return baby;

        DyeColor color1 = this.getVariant().getDyeColor();
        DyeColor color2 = otherCrab.getVariant().getDyeColor();

        DyeColor mixedColor = getMixedDyeColor(level, color1, color2);
        baby.setVariant(CrabVariant.fromDyeColor(mixedColor != null ? mixedColor : color1));

        return baby;
    }

    @Nullable
    private static DyeColor getMixedDyeColor(ServerLevel level, DyeColor color1, DyeColor color2) {
        if (color1 == color2) return color1;

        RecipeManager recipeManager = level.getRecipeManager();

        for (RecipeHolder<CraftingRecipe> holder : recipeManager.getAllRecipesFor(RecipeType.CRAFTING)) {
            CraftingRecipe recipe = holder.value();

            if (recipe instanceof ShapelessRecipe shapeless
                    && shapeless.getResultItem(level.registryAccess()).getItem() instanceof net.minecraft.world.item.DyeItem resultDye
                    && shapeless.getIngredients().size() == 2) { // Only allow recipes with 2 ingredients

                List<DyeColor> inputColors = shapeless.getIngredients().stream()
                        .map(ingredient -> {
                            ItemStack[] stacks = ingredient.getItems();
                            if (stacks.length > 0 && stacks[0].getItem() instanceof net.minecraft.world.item.DyeItem dyeItem) {
                                return dyeItem.getDyeColor();
                            }
                            return null;
                        })
                        .filter(c -> c != null)
                        .toList();

                if (inputColors.size() == 2 &&
                        ((inputColors.get(0) == color1 && inputColors.get(1) == color2) ||
                                (inputColors.get(0) == color2 && inputColors.get(1) == color1))) {
                    return resultDye.getDyeColor();
                }
            }
        }

        return null;
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.of(
                    CDModTags.CRAB_TEMPT_ITEM);

        return temptationItems;
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, true);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return !stack.isEmpty() && getTemptationItems().test(stack);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void saveToBucketTag(@Nonnull ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, tag -> {
            tag.putInt("Age", this.getAge());
            tag.putInt("Variant", this.getTypeVariant());
        });
    }

    @Override
    @SuppressWarnings("deprecation")
    public void loadFromBucketTag(@Nonnull CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        int i = tag.getInt("Variant");
        if (i >= 0 && i < this.getTypeVariant()) {
            this.setVariant(this.getVariant());
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(CDModItems.CRAB_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }

    @Override
    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        var result = Bucketable.bucketMobPickup(player, hand, this);
        if (result.isPresent()) {
            return result.get();
        }

        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item instanceof DyeItem dyeItem) {
            DyeColor dyeColor = dyeItem.getDyeColor();
            CrabVariant newVariant = CrabVariant.fromDyeColor(dyeColor);

            if (newVariant != this.getVariant()) {
                this.setVariant(newVariant);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }
}