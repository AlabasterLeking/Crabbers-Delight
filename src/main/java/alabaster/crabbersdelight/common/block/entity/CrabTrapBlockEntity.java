package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.block.container.CrabTrapMenu;
import alabaster.crabbersdelight.common.block.entity.inventory.CrabTrapItemHandler;
import alabaster.crabbersdelight.common.registry.ModBlockEntity;
import alabaster.crabbersdelight.common.tags.CDModTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class CrabTrapBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    public static final Component CRAB_TRAP_NAME = Component.translatable("block.crabbersdelight.crab_trap");

    private final CrabTrapItemHandler inventory = new CrabTrapItemHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final LazyOptional<IItemHandler> input = LazyOptional.of(() -> new RangedWrapper(this.inventory, 0, 1));
    private final LazyOptional<IItemHandler> output = LazyOptional.of(() -> new RangedWrapper(this.inventory, 1, 19));
    private int tickCounter = 0;

    public CrabTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRAB_TRAP.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("handler", this.inventory.serializeNBT());
        tag.putInt("tickCounter", tickCounter);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.inventory.deserializeNBT(tag.getCompound("handler"));
        this.tickCounter = tag.getInt("tickCounter");
    }

    private CompoundTag saveItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("handler", this.inventory.serializeNBT());
        return compound;
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveItems(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        this.load(packet.getTag());
    }

    public static Pair<Integer, Integer> getMinMax() {
        return Pair.of(Config.MIN_TICKS.get(), Config.MAX_TICKS.get());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CrabTrapBlockEntity blockEntity) {
        RandomSource random = level.getRandom();
        if (getMinMax().getSecond() > getMinMax().getFirst()) {
            if (blockEntity.tickCounter >= random.nextIntBetweenInclusive(getMinMax().getFirst(), getMinMax().getSecond())) {
                blockEntity.tickCounter = 0;
                if (isValidFishingLocation(level, pos)) {
                    LootParams lootparams = (new LootParams.Builder((ServerLevel) level))
                            .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                            .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                            .withParameter(LootContextParams.BLOCK_ENTITY, blockEntity)
                            .create(LootContextParamSets.FISHING);
                    ItemStack itemInBaitSlot = blockEntity.inventory.getStackInSlot(0);
                    LootTable loottable;

                    if (itemInBaitSlot.is(CDModTags.CRAB_TRAP_BAIT)) {
                        if (itemInBaitSlot.is(CDModTags.CREATURE_CHUMS)) {
                            ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(itemInBaitSlot.getItem());
                            ResourceLocation lootTableLocation = CrabbersDelight.modPrefix("gameplay/crab_trap_loot/" + Objects.requireNonNull(registryName).getNamespace() + "/" + registryName.getPath());
                            loottable = level.getServer().getLootData().getLootTable(lootTableLocation);
                            List<ItemStack> list = loottable.getRandomItems(lootparams);
                            blockEntity.inventory.addItemsAndShrinkBait(level, pos, state, list, itemInBaitSlot);
                        } else {
                            if (isTreasureFishingLocation(level, pos)) {
                                ResourceLocation lootTableLocation = CrabbersDelight.modPrefix("gameplay/crab_trap_loot/minecraft/treasure");
                                loottable = level.getServer().getLootData().getLootTable(lootTableLocation);
                                List<ItemStack> list = loottable.getRandomItems(lootparams);
                                blockEntity.inventory.addItemsAndShrinkBait(level, pos, state, list, itemInBaitSlot);
                            }
                            else {
                                ResourceLocation lootTableLocation = CrabbersDelight.modPrefix("gameplay/crab_trap_loot/minecraft/junk");
                                loottable = level.getServer().getLootData().getLootTable(lootTableLocation);
                                List<ItemStack> list = loottable.getRandomItems(lootparams);
                                blockEntity.inventory.addItemsAndShrinkBait(level, pos, state, list, itemInBaitSlot);
                            }
                        }
                    }
                }

            } else {
                blockEntity.tickCounter++;
            }
        } else {
            CrabbersDelight.LOGGER.error("Error: Miniumum value is higher than maximum value!");
        }
    }

    private static boolean isValidFishingLocation(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) {
                if (level.getFluidState(pos.relative(direction)).is(FluidTags.WATER)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isTreasureFishingLocation(Level level, BlockPos pos) {
        for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-2, 0, -2), pos.offset(2, 2, 2))) {
            if (!level.getFluidState(nearbyPos).is(FluidTags.WATER)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            if (side == null || side.equals(Direction.UP)) {
                return input.cast();
            } else {
                return output.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public CrabTrapItemHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Component getName() {
        return CRAB_TRAP_NAME;
    }

    @Override
    public Component getDisplayName() {
        return CRAB_TRAP_NAME;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new CrabTrapMenu(id, playerInv, this.inventory);
    }
}