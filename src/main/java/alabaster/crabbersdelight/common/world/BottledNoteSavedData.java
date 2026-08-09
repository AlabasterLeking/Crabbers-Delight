package alabaster.crabbersdelight.common.world;

import alabaster.crabbersdelight.common.Config;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BottledNoteSavedData extends SavedData {
    private static final String DATA_NAME = "crabbersdelight_bottled_notes";
    private static final int MAX_QUEUED_NOTES = 500;

    public record Entry(String title, String text, String author, int generation, ItemStack reward) {}

    private final Deque<Entry> queue = new ArrayDeque<>();

    public static BottledNoteSavedData get(ServerLevel level) {
        DimensionDataStorage storage = level.getServer().overworld().getDataStorage();
        return storage.computeIfAbsent(
                new SavedData.Factory<>(BottledNoteSavedData::new, BottledNoteSavedData::load, null),
                DATA_NAME
        );
    }

    private static BottledNoteSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        BottledNoteSavedData data = new BottledNoteSavedData();
        ListTag list = tag.getList("Notes", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entryTag = list.getCompound(i);
            ItemStack reward = entryTag.contains("Reward")
                    ? ItemStack.parseOptional(registries, entryTag.getCompound("Reward"))
                    : ItemStack.EMPTY;
            data.queue.addLast(new Entry(entryTag.getString("Title"), entryTag.getString("Text"), entryTag.getString("Author"), entryTag.getInt("Generation"), reward));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (Entry entry : queue) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putString("Title", entry.title());
            entryTag.putString("Text", entry.text());
            entryTag.putString("Author", entry.author());
            entryTag.putInt("Generation", entry.generation());
            if (!entry.reward().isEmpty()) {
                entryTag.put("Reward", entry.reward().save(registries));
            }
            list.add(entryTag);
        }
        tag.put("Notes", list);
        return tag;
    }

    public void addNote(String title, String text, String author, int generation, ItemStack reward) {
        queue.addLast(new Entry(title, text, author, generation, reward));
        while (queue.size() > MAX_QUEUED_NOTES) {
            queue.pollFirst();
        }
        setDirty();
    }

    public List<Entry> asList() {
        return new ArrayList<>(queue);
    }

    public void remove(Entry entry) {
        if (!Config.THROWN_NOTES_PERSIST.get()) {
            queue.remove(entry);
            setDirty();
        }
    }
}