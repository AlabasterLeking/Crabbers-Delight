package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.CrabTrapBlock;
import alabaster.crabbersdelight.common.block.NautilusShellBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, CrabbersDelight.MODID);

    // CrabEntity Trap
    public static final Supplier<Block> CRAB_TRAP = BLOCKS.register("crab_trap",
            () -> new CrabTrapBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    // Storage
    public static final Supplier<Block> CRAB_BARREL = BLOCKS.register("crab_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> CLAM_BARREL = BLOCKS.register("clam_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> CLAWSTER_BARREL = BLOCKS.register("clawster_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> SHRIMP_BARREL = BLOCKS.register("shrimp_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> COD_BARREL = BLOCKS.register("cod_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> SALMON_BARREL = BLOCKS.register("salmon_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> PUFFERFISH_BARREL = BLOCKS.register("pufferfish_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> TROPICAL_FISH_BARREL = BLOCKS.register("tropical_fish_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> SQUID_BARREL = BLOCKS.register("squid_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> GLOW_SQUID_BARREL = BLOCKS.register("glow_squid_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> FROG_LEG_BARREL = BLOCKS.register("frog_leg_barrel",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    //public static final Supplier<Block> LANTERNFISH_BARREL = BLOCKS.register("lanternfish_barrel",
            //() -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> NAUTILUS_SHELL_BLOCK = BLOCKS.register("nautilus_shell_block",
            () -> new NautilusShellBlock(Block.Properties.ofFullCopy(Blocks.PRISMARINE).strength(2.0F, 3.0F).sound(SoundType.CORAL_BLOCK)));

    public static final Supplier<Block> PEARL_BLOCK = BLOCKS.register("pearl_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.PRISMARINE).strength(2.0F, 3.0F).sound(SoundType.CORAL_BLOCK)));

    // Feast
}