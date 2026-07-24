package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.item.component.NoteContent;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
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
}