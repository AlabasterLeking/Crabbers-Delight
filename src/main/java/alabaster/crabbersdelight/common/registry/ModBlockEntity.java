package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CrabbersDelight.MODID);

    public static final RegistryObject<BlockEntityType<CrabTrapBlockEntity>> CRAB_TRAP = BLOCK_ENTITY_TYPE.register("crab_trap",
            () -> BlockEntityType.Builder.of(CrabTrapBlockEntity::new, new Block[]{ModBlocks.CRAB_TRAP.get()}).build(null));
}
