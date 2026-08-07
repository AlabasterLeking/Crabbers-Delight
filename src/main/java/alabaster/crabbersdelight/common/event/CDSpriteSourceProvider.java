package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.container.CrabTrapMenu;
import alabaster.crabbersdelight.common.block.container.TackleBoxMenu;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CDSpriteSourceProvider extends SpriteSourceProvider {

    public CDSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        super(output,lookupProvider, CrabbersDelight.MODID, fileHelper);
    }

    @Override
    protected void gather() {
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(CrabTrapMenu.BAIT_SLOT, Optional.empty()));
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(TackleBoxMenu.BAIT_SLOT_ICON, Optional.empty()));
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(TackleBoxMenu.LINE_SLOT_ICON, Optional.empty()));
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(TackleBoxMenu.LURE_SLOT_ICON, Optional.empty()));
    }
}