package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.client.gui.NoteClientHandler;
import alabaster.crabbersdelight.common.block.NoteBlock;
import alabaster.crabbersdelight.common.block.entity.NoteBlockEntity;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SignedNoteItem extends BlockItem {
    public SignedNoteItem(Item.Properties properties) {
        super(CDModBlocks.NOTE_BLOCK.get(), properties);
    }

    @Override
    public String getDescriptionId() {
        return Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
    }

    @Override
    public Component getName(ItemStack stack) {
        SignedNoteContent content = stack.get(CDModDataComponents.SIGNED_NOTE_CONTENT.get());
        if (content != null && !content.title().isBlank()) {
            return Component.literal(content.title());
        }
        return super.getName(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        SignedNoteContent content = context.getItemInHand().get(CDModDataComponents.SIGNED_NOTE_CONTENT.get());

        boolean canWall = canAttachToWall(context);
        boolean canFlat = canAttachFlat(context);

        if (!canWall && !canFlat) {
            if (level.isClientSide && content != null) {
                NoteClientHandler.openReadScreen(content.title(), content.text(), content.author());
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        InteractionResult result = place(placeContext);
        if (result.consumesAction() && !level.isClientSide && content != null) {
            BlockPos placedPos = placeContext.getClickedPos();
            if (level.getBlockEntity(placedPos) instanceof NoteBlockEntity note) {
                note.signDirectly(content.title(), content.text(), content.author(), content.generation());
                BlockState signedState = level.getBlockState(placedPos).setValue(NoteBlock.SIGNED, true);
                level.setBlock(placedPos, signedState, Block.UPDATE_ALL);
                level.sendBlockUpdated(placedPos, signedState, signedState, Block.UPDATE_ALL);
            }
        }
        return result;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            SignedNoteContent content = stack.get(CDModDataComponents.SIGNED_NOTE_CONTENT.get());
            if (content != null) {
                NoteClientHandler.openReadScreen(content.title(), content.text(), content.author());
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        SignedNoteContent content = stack.get(CDModDataComponents.SIGNED_NOTE_CONTENT.get());
        if (content != null) {
            tooltip.add(Component.translatable("item.crabbersdelight.signed_note.author", content.author()).withStyle(ChatFormatting.GRAY));
            switch (content.generation()) {
                case SignedNoteContent.GENERATION_ORIGINAL ->
                        tooltip.add(Component.translatable("gui.crabbersdelight.note.generation.original").withStyle(ChatFormatting.GRAY));
                case SignedNoteContent.GENERATION_COPY ->
                        tooltip.add(Component.translatable("gui.crabbersdelight.note.generation.copy").withStyle(ChatFormatting.GRAY));
                case SignedNoteContent.GENERATION_COPY_OF_COPY ->
                        tooltip.add(Component.translatable("gui.crabbersdelight.note.generation.copy_of_copy").withStyle(ChatFormatting.GRAY));
                case SignedNoteContent.GENERATION_TATTERED ->
                        tooltip.add(Component.translatable("gui.crabbersdelight.note.generation.tattered").withStyle(ChatFormatting.GRAY));
                default -> {
                }
            }
        }
    }

    private boolean canAttachToWall(UseOnContext context) {
        Direction face = context.getClickedFace();
        if (!face.getAxis().isHorizontal()) {
            return false;
        }
        Level level = context.getLevel();
        BlockPos wallPos = context.getClickedPos();
        return level.getBlockState(wallPos).isCollisionShapeFullBlock(level, wallPos);
    }

    private boolean canAttachFlat(UseOnContext context) {
        if (context.getClickedFace() != Direction.UP) {
            return false;
        }
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return level.getBlockState(pos).isCollisionShapeFullBlock(level, pos);
    }
}