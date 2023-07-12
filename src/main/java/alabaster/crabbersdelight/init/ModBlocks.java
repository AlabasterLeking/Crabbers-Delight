package alabaster.crabbersdelight.init;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static alabaster.crabbersdelight.CrabbersDelight.BLOCKS;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrabbersDelight.MODID);

    public static final RegistryObject<Block> CRAB_BARREL = BLOCKS.register("crab_barrel.json",
            () -> new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
}
