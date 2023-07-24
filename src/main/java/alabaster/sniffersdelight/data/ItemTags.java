package alabaster.sniffersdelight.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import alabaster.sniffersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import alabaster.sniffersdelight.common.tags.CDModTags;

import javax.annotation.Nullable;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {

        this.registerModTags();
    }

    private void registerModTags() {

    }
}