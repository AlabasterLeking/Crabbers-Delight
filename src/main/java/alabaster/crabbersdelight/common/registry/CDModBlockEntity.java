package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.CDHangingSignBlockEntity;
import alabaster.crabbersdelight.common.block.entity.CDSignBlockEntity;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import alabaster.crabbersdelight.common.block.entity.FishPlaqueBlockEntity;
import alabaster.crabbersdelight.common.block.entity.NoteBlockEntity;
import alabaster.crabbersdelight.common.block.entity.TackleBoxBlockEntity;
import alabaster.crabbersdelight.common.block.entity.WormBinBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.entity.CabinetBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.function.Supplier;


@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CDModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CrabbersDelight.MODID);

    public static final Supplier<BlockEntityType<CrabTrapBlockEntity>> CRAB_TRAP = BLOCK_ENTITY_TYPES.register("crab_trap",
            () -> BlockEntityType.Builder.of(CrabTrapBlockEntity::new, CDModBlocks.CRAB_TRAP.get()).build(null));

    public static final Supplier<BlockEntityType<FishPlaqueBlockEntity>> FISH_PLAQUE = BLOCK_ENTITY_TYPES.register("fish_plaque",
            () -> BlockEntityType.Builder.of(FishPlaqueBlockEntity::new, CDModBlocks.FISH_PLAQUE.get()).build(null));

    public static final Supplier<BlockEntityType<NoteBlockEntity>> NOTE = BLOCK_ENTITY_TYPES.register("note",
            () -> BlockEntityType.Builder.of(NoteBlockEntity::new, CDModBlocks.NOTE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<TackleBoxBlockEntity>> TACKLE_BOX = BLOCK_ENTITY_TYPES.register("tackle_box",
            () -> BlockEntityType.Builder.of(TackleBoxBlockEntity::new, CDModBlocks.TACKLE_BOX.get()).build(null));

    public static final Supplier<BlockEntityType<WormBinBlockEntity>> WORM_BIN = BLOCK_ENTITY_TYPES.register("worm_bin",
            () -> BlockEntityType.Builder.of(WormBinBlockEntity::new, CDModBlocks.WORM_BIN.get()).build(null));

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

    @SubscribeEvent
    public static void addCabinetsBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(ModBlockEntityTypes.CABINET.get(),
                CDModBlocks.PALM_CABINET.get()
        );
    }
}