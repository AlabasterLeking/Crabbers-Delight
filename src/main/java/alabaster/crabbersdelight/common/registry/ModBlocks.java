package alabaster.crabbersdelight.common.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import alabaster.crabbersdelight.CrabbersDelight;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrabbersDelight.MODID);

    // Food Storage
    public static final RegistryObject<Block> CRAB_BARREL = BLOCKS.register("crab_barrel",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)));

    //private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
    //RegistryObject<T> block = BLOCKS.register(name, supplier);
    //ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    //return block;
}