package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.inventory.WormBinItemHandler;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class WormBinBlockEntity extends BlockEntity {
    public static final int DEGRADE_TIME = 1200;
    public static final int MAX_WORMS = 16;
    private static final float PARTICLE_CHANCE = 0.1f;

    private final WormBinItemHandler handler = new WormBinItemHandler();
    private final IItemHandler fuelInput = new RangedWrapper(handler, WormBinItemHandler.FUEL_SLOT, WormBinItemHandler.FUEL_SLOT + 1);
    private final IItemHandler wormOutput = new RangedWrapper(handler, WormBinItemHandler.WORM_SLOT, WormBinItemHandler.WORM_SLOT + 1);
    private int progress = 0;

    public WormBinBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.WORM_BIN.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WormBinBlockEntity be) {
        ItemStack fuel = be.handler.getStackInSlot(WormBinItemHandler.FUEL_SLOT);
        boolean changed = false;

        if (fuel.isEmpty()) {
            be.progress = 0;
        } else {
            be.progress++;
            if (be.progress >= DEGRADE_TIME) {
                be.progress = 0;

                float chance = getCompostChance(fuel);
                int yield = 1 + Math.round(chance * 2);

                ItemStack currentWorms = be.handler.getStackInSlot(WormBinItemHandler.WORM_SLOT);
                int newCount = Math.min(MAX_WORMS, currentWorms.getCount() + yield);
                be.handler.setStackInSlot(WormBinItemHandler.WORM_SLOT, new ItemStack(CDModItems.WORM.get(), newCount));

                fuel.shrink(1);
                changed = true;

                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                            10, 0.25, 0.15, 0.25, 0.0);
                }
            }
        }

        if (!fuel.isEmpty() && level instanceof ServerLevel serverLevel && level.getRandom().nextFloat() < PARTICLE_CHANCE) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PODZOL.defaultBlockState()),
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    2, 0.2, 0.05, 0.2, 0.0);
        }

        if (changed) {
            be.setChanged();
        }
    }

    public static boolean isValidFuel(ItemStack stack) {
        return getCompostChanceOrNegative(stack) >= 0f;
    }

    private static float getCompostChance(ItemStack stack) {
        float chance = getCompostChanceOrNegative(stack);
        return chance >= 0f ? chance : 0f;
    }

    private static float getCompostChanceOrNegative(ItemStack stack) {
        Compostable data = stack.getItemHolder().getData(NeoForgeDataMaps.COMPOSTABLES);
        return data != null ? data.chance() : -1f;
    }

    public boolean insertFuel(ItemStack stack) {
        if (!isValidFuel(stack) || !handler.getStackInSlot(WormBinItemHandler.FUEL_SLOT).isEmpty()) {
            return false;
        }
        handler.setStackInSlot(WormBinItemHandler.FUEL_SLOT, stack.copyWithCount(1));
        progress = 0;
        stack.shrink(1);
        setChanged();
        return true;
    }

    public ItemStack ejectFuel() {
        ItemStack fuel = handler.getStackInSlot(WormBinItemHandler.FUEL_SLOT);
        if (fuel.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack ejected = fuel.copy();
        handler.setStackInSlot(WormBinItemHandler.FUEL_SLOT, ItemStack.EMPTY);
        progress = 0;
        setChanged();
        return ejected;
    }

    public ItemStack collectWorms() {
        ItemStack worms = handler.getStackInSlot(WormBinItemHandler.WORM_SLOT);
        if (worms.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack collected = worms.copy();
        handler.setStackInSlot(WormBinItemHandler.WORM_SLOT, ItemStack.EMPTY);
        setChanged();
        return collected;
    }

    public WormBinItemHandler getHandler() {
        return handler;
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CDModBlockEntity.WORM_BIN.get(),
                (be, context) -> context == Direction.UP ? be.fuelInput : be.wormOutput
        );
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", handler.serializeNBT(registries));
        tag.putInt("Progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            handler.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
        progress = tag.getInt("Progress");
    }
}