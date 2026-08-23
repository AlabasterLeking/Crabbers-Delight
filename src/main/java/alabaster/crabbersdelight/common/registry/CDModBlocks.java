package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.*;
import alabaster.crabbersdelight.common.block.NoteBlock;
import alabaster.crabbersdelight.common.worldgen.tree.CDTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.CabinetBlock;

import java.util.function.Supplier;

public class CDModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, CrabbersDelight.MODID);

    // Crab Trap
    public static final Supplier<Block> CRAB_TRAP = BLOCKS.register("crab_trap",
            () -> new CrabTrapBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> FISH_PLAQUE = BLOCKS.register("fish_plaque",
            () -> new FishPlaqueBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).noOcclusion()));

    public static final Supplier<Block> NOTE_BLOCK = BLOCKS.register("note_block",
            () -> new NoteBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).noOcclusion().strength(0.1F).noLootTable()));

    public static final Supplier<Block> TACKLE_BOX = BLOCKS.register("tackle_box",
            () -> new TackleBoxBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).noOcclusion().strength(2.5F).noLootTable()));

    // Palm Tree
    public static final Supplier<Block> PALM_LOG = BLOCKS.register("palm_log",
            () -> new CDLogBlock(Block.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final Supplier<Block> PALM_WOOD = BLOCKS.register("palm_wood",
            () -> new CDLogBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final Supplier<Block> STRIPPED_PALM_LOG = BLOCKS.register("stripped_palm_log",
            () -> new CDLogBlock(Block.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final Supplier<Block> STRIPPED_PALM_WOOD = BLOCKS.register("stripped_palm_wood",
            () -> new CDLogBlock(Block.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final Supplier<Block> PALM_PLANKS = BLOCKS.register("palm_planks",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final Supplier<Block> PALM_LEAVES = BLOCKS.register("palm_leaves",
            () -> new CDLeavesBlock(Block.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final Supplier<Block> PALM_SAPLING = BLOCKS.register("palm_sapling",
            () -> new CDSaplingBlock(CDTreeGrowers.PALM, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> Blocks.SAND));

    public static final Supplier<SlabBlock> PALM_SLAB = BLOCKS.register("palm_slab",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final Supplier<StairBlock> PALM_STAIRS = BLOCKS.register("palm_stairs",
            () -> new StairBlock(Blocks.OAK_PLANKS.defaultBlockState(), Block.Properties.ofFullCopy(Blocks.OAK_STAIRS)));

    public static final Supplier<DoorBlock> PALM_DOOR = BLOCKS.register("palm_door",
            () -> new DoorBlock(BlockSetType.OAK, Block.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    public static final Supplier<TrapDoorBlock> PALM_TRAPDOOR = BLOCKS.register("palm_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));

    public static final Supplier<FenceBlock> PALM_FENCE = BLOCKS.register("palm_fence",
            () -> new FenceBlock(Block.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final Supplier<FenceGateBlock> PALM_FENCE_GATE = BLOCKS.register("palm_fence_gate",
            () -> new FenceGateBlock(CDWoodTypes.PALM, Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final Supplier<PressurePlateBlock> PALM_PRESSURE_PLATE = BLOCKS.register("palm_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final Supplier<ButtonBlock> PALM_BUTTON = BLOCKS.register("palm_button",
            () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

    public static final Supplier<StandingSignBlock> PALM_SIGN = BLOCKS.register("palm_sign",
            () -> new CDStandingSignBlock(CDWoodTypes.PALM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final Supplier<WallSignBlock> PALM_WALL_SIGN = BLOCKS.register("palm_wall_sign",
            () -> new CDWallSignBlock(CDWoodTypes.PALM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));

    public static final Supplier<CeilingHangingSignBlock> PALM_HANGING_SIGN = BLOCKS.register("palm_hanging_sign",
            () -> new CDHangingSignBlock(CDWoodTypes.PALM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final Supplier<WallHangingSignBlock> PALM_WALL_HANGING_SIGN = BLOCKS.register("palm_wall_hanging_sign",
            () -> new CDWallHangingSignBlock(CDWoodTypes.PALM, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)));

    public static final Supplier<Block> PALM_CABINET = BLOCKS.register("palm_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final Supplier<Block> COCONUT = BLOCKS.register("coconut",
            () -> new CoconutBlock(Block.Properties.ofFullCopy(Blocks.COCOA)));

    public static final Supplier<Block> SEASHELLS = BLOCKS.register("seashells",
            () -> new SeashellBlock(Block.Properties.ofFullCopy(Blocks.HORN_CORAL).offsetType(BlockBehaviour.OffsetType.XZ)));

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

    public static final Supplier<Block> COCONUT_CRATE = BLOCKS.register("coconut_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> NAUTILUS_SHELL_BLOCK = BLOCKS.register("nautilus_shell_block",
            () -> new NautilusShellBlock(Block.Properties.ofFullCopy(Blocks.PRISMARINE).strength(2.0F, 3.0F).sound(SoundType.CORAL_BLOCK)));

    public static final Supplier<Block> PEARL_BLOCK = BLOCKS.register("pearl_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.PRISMARINE).strength(2.0F, 3.0F).sound(SoundType.CORAL_BLOCK)));

    public static final Supplier<Block> SCUTE_BLOCK = BLOCKS.register("scute_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.PRISMARINE).strength(2.0F, 3.0F).sound(SoundType.CORAL_BLOCK)));

    public static final Supplier<Block> SEA_PICKLE_CRATE = BLOCKS.register("sea_pickle_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> WORM_BIN = BLOCKS.register("worm_bin",
            () -> new WormBinBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD)));

    public static final Supplier<Block> WORMY_DIRT = BLOCKS.register("wormy_dirt",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.DIRT)));
}