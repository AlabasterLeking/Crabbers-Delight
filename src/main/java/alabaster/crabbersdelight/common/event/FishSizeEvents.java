package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.item.component.FishSize;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishSizeEvents {

    private static final double LUCK_PER_SIZE_STEP = 3.0;
    private static final double WEIGHT_FALLOFF_PER_STEP = 0.3;
    private static final double MIN_WEIGHT = 0.01;

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (!Config.FISH_SIZE_ENABLED.get()) {
            return;
        }
        Player player = event.getEntity();
        for (ItemStack stack : event.getDrops()) {
            rollAndApplySize(player, stack);
        }
    }

    public static void rollAndApplySize(Player player, ItemStack stack) {
        if (!Config.FISH_SIZE_ENABLED.get()) {
            return;
        }
        if (!stack.is(ItemTags.FISHES)) {
            return;
        }
        FishSize size = rollSize(player);
        if (size == FishSize.REGULAR) {
            return;
        }
        stack.set(CDModDataComponents.FISH_SIZE.get(), size);
        applyScaledFood(stack, size);
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!Config.FISH_SIZE_ENABLED.get()) {
            return;
        }
        FishSize size = event.getItemStack().get(CDModDataComponents.FISH_SIZE.get());
        if (size == null) {
            return;
        }
        event.getToolTip().add(Component.translatable("tooltip.crabbersdelight.fish_size." + size.getSerializedName())
                .withStyle(size.getColor()));
    }

    @SubscribeEvent
    public static void onFinishEatingFish(LivingEntityUseItemEvent.Finish event) {
        if (!Config.FISH_SIZE_ENABLED.get()) {
            return;
        }
        ItemStack stack = event.getItem();
        FishSize size = stack.get(CDModDataComponents.FISH_SIZE.get());
        if (size == null || size == FishSize.REGULAR) {
            return;
        }
        FoodProperties defaultFood = stack.getItem().getDefaultInstance().get(DataComponents.FOOD);
        if (defaultFood == null || defaultFood.effects().isEmpty()) {
            return;
        }

        LivingEntity entity = event.getEntity();
        RandomSource random = entity.getRandom();

        for (FoodProperties.PossibleEffect possibleEffect : defaultFood.effects()) {
            if (random.nextFloat() >= possibleEffect.probability()) {
                continue;
            }
            MobEffectInstance base = possibleEffect.effectSupplier().get();
            entity.addEffect(new MobEffectInstance(base));
        }
    }

    private static FishSize rollSize(Player player) {
        double luck = player.getAttributeValue(Attributes.LUCK);
        double center = 2.0 + luck / LUCK_PER_SIZE_STEP; // index 2 = REGULAR at neutral luck

        FishSize[] sizes = FishSize.values();
        double[] weights = new double[sizes.length];
        double totalWeight = 0;
        for (int i = 0; i < sizes.length; i++) {
            double distance = Math.abs(i - center);
            weights[i] = Math.max(MIN_WEIGHT, 1.0 - distance * WEIGHT_FALLOFF_PER_STEP);
            totalWeight += weights[i];
        }

        RandomSource random = player.getRandom();
        double roll = random.nextDouble() * totalWeight;
        double cumulative = 0;
        for (int i = 0; i < sizes.length; i++) {
            cumulative += weights[i];
            if (roll < cumulative) {
                return sizes[i];
            }
        }
        return FishSize.REGULAR;
    }

    public static void applyScaledFood(ItemStack stack, FishSize size) {
        FoodProperties base = stack.get(DataComponents.FOOD);
        if (base == null) {
            return;
        }
        float multiplier = size.getMultiplier();

        FoodProperties scaled = new FoodProperties(
                Math.round(base.nutrition() * multiplier),
                base.saturation() * multiplier,
                base.canAlwaysEat(),
                base.eatSeconds(),
                base.usingConvertsTo(),
                List.of()
        );
        stack.set(DataComponents.FOOD, scaled);
    }
}