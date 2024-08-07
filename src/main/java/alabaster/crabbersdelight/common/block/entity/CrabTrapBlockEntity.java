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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import javax.annotation.Nonnull;
import java.util.List;

import static alabaster.crabbersdelight.common.Config.REQUIRE_SURROUNDING_WATER;

public class CrabTrapBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    public static final Component CRAB_TRAP_NAME = Component.translatable("block.crabbersdelight.crab_trap");

    private final CrabTrapItemHandler inventory = new CrabTrapItemHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final IItemHandler input = new RangedWrapper(this.inventory, 0, 1);
    private final IItemHandler output = new RangedWrapper(this.inventory, 1, 28);
    private int tickCounter = 0;

    public CrabTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRAB_TRAP.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
        tag.putInt("tickCounter", tickCounter);
    }


    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.tickCounter = tag.getInt("tickCounter");
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private CompoundTag saveItems(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.saveAdditional(compound, pRegistries);
        compound.put("handler", this.inventory.serializeNBT(pRegistries));
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveItems(new CompoundTag(), registries);
    }

    public static Pair<Integer, Integer> getMinMax() {
        return Pair.of(Config.MIN_TICKS.get(), Config.MAX_TICKS.get());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CrabTrapBlockEntity blockEntity) {
        RandomSource random = level.getRandom();
        if (getMinMax().getSecond() > getMinMax().getFirst()) {
            if (blockEntity.tickCounter >= random.nextIntBetweenInclusive(getMinMax().getFirst(), getMinMax().getSecond())) {
                blockEntity.tickCounter = 0;
                if (isSurroundedByWater(level, pos)) {
                    if (isValidFishingLocation(level, pos)) {
                        LootParams lootparams = (new LootParams.Builder((ServerLevel) level))
                                .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                .withParameter(LootContextParams.BLOCK_ENTITY, blockEntity)
                                .create(LootContextParamSets.FISHING);
                        ItemStack itemInBaitSlot = blockEntity.inventory.getStackInSlot(0);
                        LootTable loottable;

                        if (itemInBaitSlot.is(CDModTags.CRAB_TRAP_BAIT)) {
                            ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(itemInBaitSlot.getItem());
                            ResourceLocation lootTableLocation = CrabbersDelight.modPrefix("gameplay/crab_trap_loot/" + registryName.getPath());
                            loottable = level.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableLocation));
                            List<ItemStack> list = loottable.getRandomItems(lootparams);
                            blockEntity.inventory.addItemsAndShrinkBait(level, pos, state, list, itemInBaitSlot);
                        }
                    }
                }

            } else {
                if (isWaterBiome(level, pos)) {
                    blockEntity.tickCounter++;
                }
                blockEntity.tickCounter++;
            }
        } else {
            CrabbersDelight.LOGGER.error("Error: Minimum value is higher than maximum value!");
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

    private static boolean isSurroundedByWater(Level level, BlockPos pos) {
        if (Config.REQUIRE_SURROUNDING_WATER.get()) {
            for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-1, 0, -1), pos.offset(1, 0, 1))) {
                if (!level.getFluidState(nearbyPos).is(FluidTags.WATER)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }


    private static boolean isWaterBiome(Level level, BlockPos pos) {
        if (level.getBiome(pos).is(Tags.Biomes.IS_AQUATIC)) {
            return true;
        }
        else {
            return false;
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntity.CRAB_TRAP.get(),
                (be, context) -> {
                    if (context == Direction.UP) {
                        return be.input;
                    }
                    return be.output;
                }
        );
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
        return new CrabTrapMenu(id, playerInv, this.inventory, ContainerLevelAccess.NULL);
    }
}