package alabaster.crabbersdelight.common.network;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.NoteBlock;
import alabaster.crabbersdelight.common.block.entity.NoteBlockEntity;
import alabaster.crabbersdelight.common.item.NoteItem;
import alabaster.crabbersdelight.common.item.component.NoteContent;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NoteNetworking {

    public record NoteEditPayload(boolean mainHand, String text) implements CustomPacketPayload {
        public static final Type<NoteEditPayload> TYPE =
                new Type<>(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "note_edit"));
        public static final StreamCodec<ByteBuf, NoteEditPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL, NoteEditPayload::mainHand,
                ByteBufCodecs.STRING_UTF8, NoteEditPayload::text,
                NoteEditPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record NoteSignPayload(boolean mainHand, String title, String text) implements CustomPacketPayload {
        public static final Type<NoteSignPayload> TYPE =
                new Type<>(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "note_sign"));
        public static final StreamCodec<ByteBuf, NoteSignPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL, NoteSignPayload::mainHand,
                ByteBufCodecs.STRING_UTF8, NoteSignPayload::title,
                ByteBufCodecs.STRING_UTF8, NoteSignPayload::text,
                NoteSignPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record BlockNoteEditPayload(BlockPos pos, String text) implements CustomPacketPayload {
        public static final Type<BlockNoteEditPayload> TYPE =
                new Type<>(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "block_note_edit"));
        public static final StreamCodec<ByteBuf, BlockNoteEditPayload> STREAM_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, BlockNoteEditPayload::pos,
                ByteBufCodecs.STRING_UTF8, BlockNoteEditPayload::text,
                BlockNoteEditPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record BlockNoteSignPayload(BlockPos pos, String title, String text) implements CustomPacketPayload {
        public static final Type<BlockNoteSignPayload> TYPE =
                new Type<>(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "block_note_sign"));
        public static final StreamCodec<ByteBuf, BlockNoteSignPayload> STREAM_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, BlockNoteSignPayload::pos,
                ByteBufCodecs.STRING_UTF8, BlockNoteSignPayload::title,
                ByteBufCodecs.STRING_UTF8, BlockNoteSignPayload::text,
                BlockNoteSignPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendEdit(InteractionHand hand, String text) {
        PacketDistributor.sendToServer(new NoteEditPayload(hand == InteractionHand.MAIN_HAND, text));
    }

    public static void sendSign(InteractionHand hand, String title, String text) {
        PacketDistributor.sendToServer(new NoteSignPayload(hand == InteractionHand.MAIN_HAND, title, text));
    }

    public static void sendBlockEdit(BlockPos pos, String text) {
        PacketDistributor.sendToServer(new BlockNoteEditPayload(pos, text));
    }

    public static void sendBlockSign(BlockPos pos, String title, String text) {
        PacketDistributor.sendToServer(new BlockNoteSignPayload(pos, title, text));
    }

    @EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class Registration {
        @SubscribeEvent
        public static void register(RegisterPayloadHandlersEvent event) {
            PayloadRegistrar registrar = event.registrar(CrabbersDelight.MODID).versioned("1");
            registrar.playToServer(NoteEditPayload.TYPE, NoteEditPayload.STREAM_CODEC, NoteNetworking::handleNoteEdit);
            registrar.playToServer(NoteSignPayload.TYPE, NoteSignPayload.STREAM_CODEC, NoteNetworking::handleNoteSign);
            registrar.playToServer(BlockNoteEditPayload.TYPE, BlockNoteEditPayload.STREAM_CODEC, NoteNetworking::handleBlockNoteEdit);
            registrar.playToServer(BlockNoteSignPayload.TYPE, BlockNoteSignPayload.STREAM_CODEC, NoteNetworking::handleBlockNoteSign);
        }
    }

    private static void handleNoteEdit(NoteEditPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            InteractionHand hand = payload.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack stack = player.getItemInHand(hand);
            if (!(stack.getItem() instanceof NoteItem)) {
                return;
            }

            ItemStack written = new ItemStack(CDModItems.NOTE.get());
            written.set(CDModDataComponents.NOTE_CONTENT.get(), new NoteContent(truncateBody(payload.text())));

            if (stack.getCount() > 1) {
                stack.shrink(1);
                if (!player.getInventory().add(written)) {
                    player.drop(written, false);
                }
            } else {
                player.setItemInHand(hand, written);
            }
        });
    }

    private static void handleNoteSign(NoteSignPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            InteractionHand hand = payload.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack stack = player.getItemInHand(hand);
            if (!(stack.getItem() instanceof NoteItem)) {
                return;
            }

            ItemStack signed = new ItemStack(CDModItems.SIGNED_NOTE.get());
            signed.set(CDModDataComponents.SIGNED_NOTE_CONTENT.get(),
                    new SignedNoteContent(truncateTitle(payload.title()), truncateBody(payload.text()), player.getGameProfile().getName(), SignedNoteContent.GENERATION_ORIGINAL));

            if (stack.getCount() > 1) {
                stack.shrink(1);
                if (!player.getInventory().add(signed)) {
                    player.drop(signed, false);
                }
            } else {
                player.setItemInHand(hand, signed);
            }
        });
    }

    private static void handleBlockNoteEdit(BlockNoteEditPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (!withinReach(player, payload.pos())) {
                return;
            }
            BlockEntity be = player.level().getBlockEntity(payload.pos());
            if (be instanceof NoteBlockEntity note && !note.isSigned()) {
                note.setUnsignedText(truncateBody(payload.text()));
                player.level().sendBlockUpdated(payload.pos(), note.getBlockState(), note.getBlockState(), 3);
            }
        });
    }

    private static void handleBlockNoteSign(BlockNoteSignPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (!withinReach(player, payload.pos())) {
                return;
            }
            BlockEntity be = player.level().getBlockEntity(payload.pos());
            if (be instanceof NoteBlockEntity note && !note.isSigned()) {
                note.signDirectly(truncateTitle(payload.title()), truncateBody(payload.text()), player.getGameProfile().getName(), SignedNoteContent.GENERATION_ORIGINAL);
                BlockState signedState = player.level().getBlockState(payload.pos()).setValue(NoteBlock.SIGNED, true);
                player.level().setBlock(payload.pos(), signedState, 3);
                player.level().sendBlockUpdated(payload.pos(), signedState, signedState, 3);
            }
        });
    }

    private static String truncateBody(String text) {
        return text.length() > CDModDataComponents.MAX_NOTE_LENGTH ? text.substring(0, CDModDataComponents.MAX_NOTE_LENGTH) : text;
    }

    private static String truncateTitle(String title) {
        return title.length() > CDModDataComponents.MAX_TITLE_LENGTH ? title.substring(0, CDModDataComponents.MAX_TITLE_LENGTH) : title;
    }

    private static boolean withinReach(ServerPlayer player, BlockPos pos) {
        return player.blockPosition().distSqr(pos) <= 64;
    }
}