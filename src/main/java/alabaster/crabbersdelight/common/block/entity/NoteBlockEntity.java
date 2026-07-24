package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NoteBlockEntity extends BlockEntity {
    private String text = "";
    private String author = "";
    private String title = "";
    private int generation = 0;
    private boolean signed = false;

    public NoteBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.NOTE.get(), pos, state);
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setUnsignedText(String text) {
        if (signed) {
            return;
        }
        this.text = text;
        setChanged();
    }

    public void signDirectly(String title, String text, String author, int generation) {
        this.text = text;
        this.title = title;
        this.author = author;
        this.generation = generation;
        this.signed = true;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("Text", text);
        tag.putString("Author", author);
        tag.putString("Title", title);
        tag.putInt("Generation", generation);
        tag.putBoolean("Signed", signed);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        text = tag.getString("Text");
        author = tag.getString("Author");
        title = tag.getString("Title");
        generation = tag.getInt("Generation");
        signed = tag.getBoolean("Signed");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Text", text);
        tag.putString("Author", author);
        tag.putString("Title", title);
        tag.putInt("Generation", generation);
        tag.putBoolean("Signed", signed);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, lookupProvider);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}