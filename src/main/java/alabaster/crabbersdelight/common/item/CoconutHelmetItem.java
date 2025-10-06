package alabaster.crabbersdelight.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;

public class CoconutHelmetItem extends Item {

    public CoconutHelmetItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
