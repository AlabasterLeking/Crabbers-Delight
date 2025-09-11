package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.Item;

import java.util.Map;

public class CDTrimMaterials {
    public static final ResourceKey<TrimMaterial> PEARL =
            ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(CrabbersDelight.MODID, "pearl"));

    public static void bootstrap(BootstapContext<TrimMaterial> context) {
        register(context, PEARL, CDModItems.PEARL.get(), Style.EMPTY.withColor(TextColor.parseColor("#86b5be")), -1.0F);
    }

    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
                                 Style style, float itemModelIndex) {
        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimmaterial);
    }
}