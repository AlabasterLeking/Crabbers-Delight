package alabaster.crabbersdelight.client.gui;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.network.NoteNetworking;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

public class NoteScreen extends Screen {
    private static final ResourceLocation BOOK_BACKGROUND = CrabbersDelight.modPrefix("textures/gui/note.png");
    private static final int IMAGE_WIDTH = 192;
    private static final int IMAGE_HEIGHT = 192;
    private static final int IMAGE_TOP = 2;
    private static final int TEXT_WIDTH = 114;
    private static final int TEXT_HEIGHT = 128;

    private final InteractionHand hand;
    private final String initialText;
    private NotePageWidget textBox;
    private NotePageWidget titleBox;
    private Button doneButton;
    private Button signButton;
    private Button cancelButton;
    private Button finalizeButton;
    private boolean isSigning;
    private int imageLeft;
    private NotePageWidget pendingFocus;

    public NoteScreen(InteractionHand hand, String initialText) {
        super(Component.translatable("gui.crabbersdelight.note.edit"));
        this.hand = hand;
        this.initialText = initialText;
    }

    @Override
    protected void init() {
        imageLeft = (width - IMAGE_WIDTH) / 2;
        int left = imageLeft + 36;

        textBox = new NotePageWidget(font, left, IMAGE_TOP + 30, TEXT_WIDTH, TEXT_HEIGHT, initialText, CDModDataComponents.MAX_NOTE_LENGTH);
        addRenderableWidget(textBox);

        titleBox = new NotePageWidget(font, left, 50, TEXT_WIDTH, 9, "", CDModDataComponents.MAX_TITLE_LENGTH, true);
        addRenderableWidget(titleBox);

        int buttonY = IMAGE_TOP + IMAGE_HEIGHT + 2;

        doneButton = addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> saveAndClose())
                .bounds(width / 2 - 100, buttonY, 98, 20).build());
        signButton = addRenderableWidget(Button.builder(Component.translatable("gui.crabbersdelight.note.sign"), b -> startSigning())
                .bounds(width / 2 + 2, buttonY, 98, 20).build());
        cancelButton = addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, b -> cancelSigning())
                .bounds(width / 2 - 100, buttonY, 98, 20).build());
        finalizeButton = addRenderableWidget(Button.builder(Component.translatable("book.finalizeButton"), b -> finalizeSign())
                .bounds(width / 2 + 2, buttonY, 98, 20).build());

        updateVisibility();
        setInitialFocus(textBox);
    }

    private void updateVisibility() {
        textBox.visible = !isSigning;
        doneButton.visible = !isSigning;
        signButton.visible = !isSigning;
        titleBox.visible = isSigning;
        cancelButton.visible = isSigning;
        finalizeButton.visible = isSigning;
        finalizeButton.active = !titleBox.getValue().isBlank();
    }

    @Override
    public void tick() {
        super.tick();
        if (isSigning) {
            finalizeButton.active = !titleBox.getValue().isBlank();
        }
        if (pendingFocus != null) {
            setInitialFocus(pendingFocus);
            pendingFocus = null;
        }
    }

    private void startSigning() {
        isSigning = true;
        updateVisibility();
        pendingFocus = titleBox;
    }

    private void cancelSigning() {
        isSigning = false;
        updateVisibility();
        pendingFocus = textBox;
    }

    private void saveAndClose() {
        NoteNetworking.sendEdit(hand, textBox.getValue());
        onClose();
    }

    private void finalizeSign() {
        NoteNetworking.sendSign(hand, titleBox.getValue(), textBox.getValue());
        onClose();
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
        if (isSigning) {
            int left = imageLeft + 36;

            Component label = Component.translatable("gui.crabbersdelight.note.editTitle");
            graphics.drawString(font, label, left + (TEXT_WIDTH - font.width(label)) / 2, 34, 0x000000, false);

            Component ownerText = Component.translatable("book.byAuthor", minecraft.player.getName());
            graphics.drawString(font, ownerText, left + (TEXT_WIDTH - font.width(ownerText)) / 2, 60, 0x000000, false);

            graphics.drawWordWrap(font, Component.translatable("gui.crabbersdelight.note.finalizeWarning"), left, 82, TEXT_WIDTH, 0x000000);
        }
    }
}