package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.CrabTrapBlock;
import alabaster.crabbersdelight.common.block.entities.CrabTrapBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CrabbersDelight.MODID);

    public static final RegistryObject<BlockEntityType<CrabTrapBlockEntity>> CRAB_TRAP = BLOCK_ENTITIES.register("crab_trap",
            () -> BlockEntityType.Builder.of(CrabTrapBlockEntity::new, ModBlocks.CRAB_TRAP.get()).build(null));
}
