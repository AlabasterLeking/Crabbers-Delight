package alabaster.crabbersdelight.client.gui;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class NoteReadScreen extends Screen {
    private static final ResourceLocation BOOK_BACKGROUND = CrabbersDelight.modPrefix("textures/gui/note.png");
    private static final int IMAGE_WIDTH = 192;
    private static final int IMAGE_HEIGHT = 192;
    private static final int IMAGE_TOP = 2;
    private static final int TEXT_WIDTH = 114;
    private static final int LINE_HEIGHT = 9;
    private static final int TEXT_COLOR = 0x000000;

    private final String title;
    private final String text;
    private final String author;
    private List<FormattedCharSequence> wrappedLines;
    private int imageLeft;

    public NoteReadScreen(String title, String text, String author) {
        super(Component.translatable("gui.crabbersdelight.note.read"));
        this.title = title;
        this.text = text;
        this.author = author;
    }

    @Override
    protected void init() {
        imageLeft = (width - IMAGE_WIDTH) / 2;
        wrappedLines = font.split(Component.literal(text), TEXT_WIDTH);

        int buttonY = IMAGE_TOP + IMAGE_HEIGHT + 2;
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> onClose())
                .bounds(width / 2 - 100, buttonY, 200, 20).build());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(graphics);
        graphics.blit(BOOK_BACKGROUND, imageLeft, IMAGE_TOP, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        int left = imageLeft + 36;
        int top = IMAGE_TOP + 30;
        boolean hasTitle = title != null && !title.isBlank();
        boolean hasAuthor = author != null && !author.isBlank();

        if (hasTitle) {
            Component titleComponent = Component.literal(title);
            graphics.drawString(font, titleComponent, left + (TEXT_WIDTH - font.width(titleComponent)) / 2, 18, TEXT_COLOR, false);
        }

        for (int i = 0; i < wrappedLines.size(); i++) {
            graphics.drawString(font, wrappedLines.get(i), left, top + i * LINE_HEIGHT, TEXT_COLOR, false);
        }

        if (hasAuthor) {
            graphics.drawString(font, "- " + author, left, top + wrappedLines.size() * LINE_HEIGHT + 6, TEXT_COLOR, false);
        }
    }
}