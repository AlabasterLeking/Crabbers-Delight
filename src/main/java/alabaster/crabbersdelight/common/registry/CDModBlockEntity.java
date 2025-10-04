package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.CDHangingSignBlockEntity;
import alabaster.crabbersdelight.common.block.entity.CDSignBlockEntity;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CDModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CrabbersDelight.MODID);

    public static final Supplier<BlockEntityType<CrabTrapBlockEntity>> CRAB_TRAP = BLOCK_ENTITY_TYPE.register("crab_trap",
            () -> BlockEntityType.Builder.of(CrabTrapBlockEntity::new, new Block[]{CDModBlocks.CRAB_TRAP.get()}).build(null));

    public static final Supplier<BlockEntityType<CDSignBlockEntity>> PALM_SIGN = BLOCK_ENTITY_TYPE.register("palm_sign",
            () -> BlockEntityType.Builder.of(CDSignBlockEntity::new,
                            CDModBlocks.PALM_SIGN.get(),
                            CDModBlocks.PALM_WALL_SIGN.get())
                    .build(null));

    public static final Supplier<BlockEntityType<CDHangingSignBlockEntity>> HANGING_PALM_SIGN = BLOCK_ENTITY_TYPE.register("hanging_palm_sign",
            () -> BlockEntityType.Builder.of(CDHangingSignBlockEntity::new,
                            CDModBlocks.PALM_HANGING_SIGN.get(),
                            CDModBlocks.PALM_WALL_HANGING_SIGN.get())
                    .build(null));
}
