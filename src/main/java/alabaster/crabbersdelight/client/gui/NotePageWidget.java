package alabaster.crabbersdelight.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class NotePageWidget extends AbstractWidget {
    private static final int LINE_HEIGHT = 9;
    private static final int TEXT_COLOR = 0x000000;

    private final Font font;
    private final int maxLength;
    private final boolean centered;
    private String text;

    public NotePageWidget(Font font, int x, int y, int width, int height, String initialText, int maxLength) {
        this(font, x, y, width, height, initialText, maxLength, false);
    }

    public NotePageWidget(Font font, int x, int y, int width, int height, String initialText, int maxLength, boolean centered) {
        super(x, y, width, height, Component.empty());
        this.font = font;
        this.maxLength = maxLength;
        this.text = initialText;
        this.centered = centered;
    }

    public String getValue() {
        return text;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        List<FormattedCharSequence> lines = wrappedLines();
        int y = getY();
        int lastLineX = centered ? getX() + getWidth() / 2 : getX();
        int lastLineWidth = 0;

        for (FormattedCharSequence line : lines) {
            int lineWidth = font.width(line);
            int x = centered ? getX() + (getWidth() - lineWidth) / 2 : getX();
            graphics.drawString(font, line, x, y, TEXT_COLOR, false);
            lastLineX = x;
            lastLineWidth = lineWidth;
            y += LINE_HEIGHT;
        }

        if (isFocused() && System.currentTimeMillis() / 300 % 2 == 0) {
            int cursorX = lastLineX + lastLineWidth;
            int cursorY = getY() + Math.max(0, lines.size() - 1) * LINE_HEIGHT;
            graphics.drawString(font, "_", cursorX, cursorY, TEXT_COLOR, false);
        }
    }

    private List<FormattedCharSequence> wrappedLines() {
        return font.split(Component.literal(text), getWidth());
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (!isFocused()) {
            return false;
        }
        if (text.length() < maxLength && StringUtil.isAllowedChatCharacter(chr)) {
            text = text + chr;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!isFocused()) {
            return false;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (!text.isEmpty()) {
                text = text.substring(0, text.length() - 1);
            }
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            if (text.length() < maxLength) {
                text = text + "\n";
            }
            return true;
        }
        return false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, Component.translatable("gui.crabbersdelight.note.edit"));
    }
}