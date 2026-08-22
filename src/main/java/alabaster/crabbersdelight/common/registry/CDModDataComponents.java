package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.item.component.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CDModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CrabbersDelight.MODID);

    public static final int MAX_NOTE_LENGTH = 255;
    public static final int MAX_TITLE_LENGTH = 16;

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<NoteContent>> NOTE_CONTENT =
            DATA_COMPONENTS.register("note_content", () -> DataComponentType.<NoteContent>builder()
                    .persistent(NoteContent.CODEC)
                    .networkSynchronized(NoteContent.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SignedNoteContent>> SIGNED_NOTE_CONTENT =
            DATA_COMPONENTS.register("signed_note_content", () -> DataComponentType.<SignedNoteContent>builder()
                    .persistent(SignedNoteContent.CODEC)
                    .networkSynchronized(SignedNoteContent.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TackleBoxContents>> TACKLE_BOX_CONTENTS =
            DATA_COMPONENTS.register("tackle_box_contents", () -> DataComponentType.<TackleBoxContents>builder()
                    .persistent(TackleBoxContents.CODEC)
                    .networkSynchronized(TackleBoxContents.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CrabTrapContents>> CRAB_TRAP_CONTENTS =
            DATA_COMPONENTS.register("crab_trap_contents", () -> DataComponentType.<CrabTrapContents>builder()
                    .persistent(CrabTrapContents.CODEC)
                    .networkSynchronized(CrabTrapContents.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BottledNoteReward>> BOTTLED_NOTE_REWARD =
            DATA_COMPONENTS.register("bottled_note_reward", () -> DataComponentType.<BottledNoteReward>builder()
                    .persistent(BottledNoteReward.CODEC)
                    .networkSynchronized(BottledNoteReward.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FishSize>> FISH_SIZE =
            DATA_COMPONENTS.register("fish_size", () -> DataComponentType.<FishSize>builder()
                    .persistent(FishSize.CODEC)
                    .networkSynchronized(FishSize.STREAM_CODEC)
                    .build());
}