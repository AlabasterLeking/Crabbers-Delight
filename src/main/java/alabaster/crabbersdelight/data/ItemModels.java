package alabaster.crabbersdelight.data;

import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModItems;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Credits to Vazkii and team for some references on mass-reading blocks to datagen!
 */
public class ItemModels extends ItemModelProvider
{
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";
    public static final ResourceLocation MUG = new ResourceLocation(CrabbersDelight.MODID, "item/mug");

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CrabbersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter(i -> CrabbersDelight.MODID.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        // Blocks whose items look alike
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

        // Generated items
        items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }

    public void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    public void itemMugModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), MUG).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(CrabbersDelight.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(CrabbersDelight.MODID, "item/" + path);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Set<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                CrabbersDelight.LOGGER.warn("Item {} not found in set", item);
            }
        }
        if (!src.removeAll(ret)) {
            CrabbersDelight.LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(items));
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            CrabbersDelight.LOGGER.warn("takeAll predicate yielded nothing", new Throwable());
        }
        return ret;
    }
}