package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CrabbersDelight.MODID);

    public static final Supplier<BlockEntityType<CrabTrapBlockEntity>> CRAB_TRAP = BLOCK_ENTITY_TYPES.register("crab_trap",
            () -> BlockEntityType.Builder.of(CrabTrapBlockEntity::new, ModBlocks.CRAB_TRAP.get()).build(null));
}