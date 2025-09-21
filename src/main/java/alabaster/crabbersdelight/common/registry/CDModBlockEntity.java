package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.CDHangingSignBlockEntity;
import alabaster.crabbersdelight.common.block.entity.CDSignBlockEntity;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CDModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CrabbersDelight.MODID);

    public static final Supplier<BlockEntityType<CrabTrapBlockEntity>> CRAB_TRAP = BLOCK_ENTITY_TYPES.register("crab_trap",
            () -> BlockEntityType.Builder.of(CrabTrapBlockEntity::new, CDModBlocks.CRAB_TRAP.get()).build(null));

    public static final Supplier<BlockEntityType<CDSignBlockEntity>> PALM_SIGN = BLOCK_ENTITY_TYPES.register("palm_sign",
            () -> BlockEntityType.Builder.of(CDSignBlockEntity::new,
                            CDModBlocks.PALM_SIGN.get(),
                            CDModBlocks.PALM_WALL_SIGN.get())
                    .build(null));

    public static final Supplier<BlockEntityType<CDHangingSignBlockEntity>> HANGING_PALM_SIGN = BLOCK_ENTITY_TYPES.register("hanging_palm_sign",
            () -> BlockEntityType.Builder.of(CDHangingSignBlockEntity::new,
                            CDModBlocks.PALM_HANGING_SIGN.get(),
                            CDModBlocks.PALM_WALL_HANGING_SIGN.get())
                    .build(null));
}