package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.common.registry.CDModBlocks;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemModels extends ItemModelProvider
{
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CrabbersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i -> CrabbersDelight.MODID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        // Specific cases
        items.remove(CDModItems.CRAB_CLAW.get());
        items.remove(CDModItems.CRAB_SPAWN_EGG.get());

        blockBasedModel(CDModItems.SEA_PICKLE_CRATE.get(), "_bottom");
        items.remove(CDModItems.SEA_PICKLE_CRATE.get());

        buttonItem(CDModItems.PALM_BUTTON.get(), CDModBlocks.PALM_PLANKS.get());
        fenceItem(CDModItems.PALM_FENCE.get(), CDModBlocks.PALM_PLANKS.get());
        items.remove(CDModItems.PALM_FENCE.get());
        items.remove(CDModItems.PALM_BUTTON.get());

        // Blocks with special item sprites
        Set<Item> spriteBlockItems = Sets.newHashSet(
                CDModItems.PALM_DOOR.get(),
                CDModItems.PALM_SIGN.get(),
                CDModItems.PALM_HANGING_SIGN.get(),
                CDModItems.COCONUT.get()
        );
        takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));


        // Blocks with flat block textures for their items
        Set<Item> flatBlockItems = Sets.newHashSet(
                CDModItems.PALM_SAPLING.get());
        takeAll(items, flatBlockItems.toArray(new Item[0])).forEach(item -> itemGeneratedModel(item, resourceBlock(itemName(item))));

        // Blocks whose item look alike
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

        // Handheld item
        Set<Item> handheldItems = Sets.newHashSet(
        );
        takeAll(items, handheldItems.toArray(new Item[0])).forEach(item -> itemHandheldModel(item, resourceItem(itemName(item))));

        // Generated item
        items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }

    public void buttonItem(Item buttonItem, Block baseBlock) {
        withExistingParent(itemName(buttonItem), mcLoc("block/button_inventory"))
                .texture("texture", resourceBlock(itemName(baseBlock.asItem())));
    }

    public void fenceItem(Item fenceItem, Block baseBlock) {
        withExistingParent(itemName(fenceItem), mcLoc("block/fence_inventory"))
                .texture("texture", resourceBlock(itemName(baseBlock.asItem())));
    }

    public void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "item/" + path);
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