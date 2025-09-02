package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.Util;

import java.util.EnumMap;
import java.util.List;

public class CDArmorMaterials {
    public static final ArmorMaterial PEARL_NECKLACE = new ArmorMaterial(
            Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 0);
                map.put(ArmorItem.Type.LEGGINGS, 0);
                map.put(ArmorItem.Type.CHESTPLATE, 0);
                map.put(ArmorItem.Type.HELMET, 0);
            }),
            25,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.of(CDModItems.PEARL.get()),
            List.of(
                    new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "pearl_necklace"))
            ),
            0.0F,
            0.0F
    );
}