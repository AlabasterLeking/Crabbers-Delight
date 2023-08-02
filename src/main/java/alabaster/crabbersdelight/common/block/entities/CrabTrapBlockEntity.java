package alabaster.crabbersdelight.common.block.entities;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModBlockEntity;
import alabaster.crabbersdelight.common.tags.CDModTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.loading.FMLEnvironment;
import alabaster.crabbersdelight.common.Config;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CrabTrapBlockEntity extends BlockEntity {

    private static final int MAX_PROGRESS = 100;
    private int progress;

    public static final Component TITLE = Component.translatable("container." + CrabbersDelight.MODID + ".crab_trap")

    private final ItemStackHandler inventory = new ItemStackHandler(10);
    private final LazyOptional<IItemHandlerModifiable> optional = LazyOptional.of(() -> this.inventory);

    public CrabTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.CRAB_TRAP.get(), pos, state);
    }

    public static Pair<Integer, Integer> getMinMax() {
        if (!FMLEnvironment.production) {
            return Pair.of(48, 80);
        }
        return Pair.of(Config.MIN_TICKS.get(), Config.MAX_TICKS.get());
    }

    public void tick() {
        progress++;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("Progress");
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Progress", this.progress);
        nbt.put("Inventory", this.inventory.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }
}