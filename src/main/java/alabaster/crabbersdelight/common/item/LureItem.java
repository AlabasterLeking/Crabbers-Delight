package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.item.fishing.LureEffect;
import net.minecraft.world.item.Item;

public class LureItem extends Item {
    private final LureEffect effect;

    public LureItem(Item.Properties properties, LureEffect effect) {
        super(properties);
        this.effect = effect;
    }

    public LureEffect getEffect() {
        return effect;
    }
}