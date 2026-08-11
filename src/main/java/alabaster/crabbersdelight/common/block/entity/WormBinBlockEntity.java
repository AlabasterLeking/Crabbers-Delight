package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.Arrays;

public class WormBinBlockEntity extends BlockEntity {
    public static final int FUEL_SLOTS = 1;
    public static final int DEGRADE_TIME = 1200;
    public static final int MAX_WORMS = 16;
    private static final float PARTICLE_CHANCE = 0.1f;

    private final NonNullList<ItemStack> fuel = NonNullList.withSize(FUEL_SLOTS, ItemStack.EMPTY);
    private final int[] progress = new int[FUEL_SLOTS];
    private int worms = 0;

    public WormBinBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.WORM_BIN.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WormBinBlockEntity be) {
        boolean changed = false;
        boolean hasActiveFuel = false;

        for (int slot = 0; slot < FUEL_SLOTS; slot++) {
            ItemStack stack = be.fuel.get(slot);
            if (stack.isEmpty()) {
                be.progress[slot] = 0;
                continue;
            }

            hasActiveFuel = true;
            be.progress[slot]++;
            if (be.progress[slot] >= DEGRADE_TIME) {
                be.progress[slot] = 0;

                float chance = getCompostChance(stack);
                int yield = 1 + Math.round(chance * 2);
                be.worms = Math.min(MAX_WORMS, be.worms + yield);

                stack.shrink(1);
                changed = true;

                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                            10, 0.25, 0.15, 0.25, 0.0);
                }
            }
        }

        if (hasActiveFuel && level instanceof ServerLevel serverLevel && level.getRandom().nextFloat() < PARTICLE_CHANCE) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ModBlocks.ORGANIC_COMPOST.get().defaultBlockState()),
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
        if (!isValidFuel(stack)) {
            return false;
        }
        for (int slot = 0; slot < FUEL_SLOTS; slot++) {
            if (fuel.get(slot).isEmpty()) {
                fuel.set(slot, stack.copyWithCount(1));
                progress[slot] = 0;
                stack.shrink(1);
                setChanged();
                return true;
            }
        }
        return false;
    }

    public java.util.List<ItemStack> ejectFuel() {
        java.util.List<ItemStack> ejected = new java.util.ArrayList<>();
        for (int slot = 0; slot < FUEL_SLOTS; slot++) {
            if (!fuel.get(slot).isEmpty()) {
                ejected.add(fuel.get(slot));
                fuel.set(slot, ItemStack.EMPTY);
                progress[slot] = 0;
            }
        }
        if (!ejected.isEmpty()) {
            setChanged();
        }
        return ejected;
    }

    public int collectWorms() {
        int collected = worms;
        worms = 0;
        setChanged();
        return collected;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Worms", worms);

        ListTag fuelList = new ListTag();
        for (int slot = 0; slot < FUEL_SLOTS; slot++) {
            CompoundTag slotTag = new CompoundTag();
            slotTag.putInt("Slot", slot);
            slotTag.putInt("Progress", progress[slot]);
            if (!fuel.get(slot).isEmpty()) {
                slotTag.put("Item", fuel.get(slot).save(registries));
            }
            fuelList.add(slotTag);
        }
        tag.put("Fuel", fuelList);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        worms = tag.getInt("Worms");

        fuel.clear();
        for (int i = 0; i < FUEL_SLOTS; i++) {
            fuel.add(ItemStack.EMPTY);
        }
        Arrays.fill(progress, 0);

        ListTag fuelList = tag.getList("Fuel", Tag.TAG_COMPOUND);
        for (int i = 0; i < fuelList.size(); i++) {
            CompoundTag slotTag = fuelList.getCompound(i);
            int slot = slotTag.getInt("Slot");
            if (slot < 0 || slot >= FUEL_SLOTS) {
                continue;
            }
            progress[slot] = slotTag.getInt("Progress");
            if (slotTag.contains("Item")) {
                fuel.set(slot, ItemStack.parseOptional(registries, slotTag.getCompound("Item")));
            }
        }
    }
}