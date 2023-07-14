package alabaster.crabbersdelight.common.registry;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.Map;

public class CDFoodValues {

        public static final int BRIEF_DURATION = 600;    // 30 seconds
        public static final int SHORT_DURATION = 1200;    // 1 minute
        public static final int MEDIUM_DURATION = 3600;    // 3 minutes
        public static final int LONG_DURATION = 6000;    // 5 minutes

        // Food Attributes
        public static final FoodProperties RAW_CRAB = (new FoodProperties.Builder())
                .nutrition(5).saturationMod(0.8f).effect(() -> new MobEffectInstance(MobEffects.POISON, 1200, 0), 0.3F).build();

        public static final FoodProperties COOKED_CRAB = (new FoodProperties.Builder())
                .nutrition(12).saturationMod(0.8f).build();

        public static final FoodProperties RAW_SHRIMP = (new FoodProperties.Builder())
                .nutrition(5).saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.POISON, 1200, 0), 0.3F).build();

        public static final FoodProperties COOKED_SHRIMP = (new FoodProperties.Builder())
                .nutrition(8).saturationMod(0.2f).build();
}

