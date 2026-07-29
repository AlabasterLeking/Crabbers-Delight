package alabaster.crabbersdelight.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;

public class NoteClientHandler {
    public static void openEditScreen(InteractionHand hand, String currentText) {
        Minecraft.getInstance().setScreen(new NoteScreen(hand, currentText));
    }

    public static void openReadScreen(String title, String text, String author) {
        Minecraft.getInstance().setScreen(new NoteReadScreen(title, text, author, false));
    }

    public static void openBlockScreen(BlockPos pos, boolean signed, String title, String text, String author) {
        if (signed) {
            Minecraft.getInstance().setScreen(new NoteReadScreen(title, text, author, true));
        } else {
            Minecraft.getInstance().setScreen(new NoteBlockEditScreen(pos, text));
        }
    }
}