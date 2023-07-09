package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Credits to Vazkii and team for some references on mass-reading blocks to datagen, and to vectorwing for adapting it to FD
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

}