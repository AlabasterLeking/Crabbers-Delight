package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.item.LureItem;
import alabaster.crabbersdelight.common.item.fishing.LureEffect;
import alabaster.crabbersdelight.common.tags.CDModTags;
import alabaster.crabbersdelight.common.utils.FishingGearUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishingLureEvents {
    private static final ResourceLocation SHINY_LUCK_ID = CrabbersDelight.modPrefix("shiny_lure_luck");
    private static final double SHINY_LUCK_BONUS = 2.0;

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        FishingGearUtil.FoundStack lure = FishingGearUtil.findFirstMatching(player, CDModTags.FISHING_LURE);
        if (lure == null || !(lure.stack().getItem() instanceof LureItem lureItem)) {
            return;
        }

        boolean caughtSomething = !event.getDrops().isEmpty();

        if (lureItem.getEffect() == LureEffect.DOUBLE && caughtSomething && player.fishing != null) {
            FishingHook hook = player.fishing;
            event.setCanceled(true);

            for (ItemStack stack : event.getDrops()) {
                spawnCaughtItem(serverLevel, hook, player, stack);
                awardCatchStat(player, stack);
            }
            for (ItemStack stack : rollSecondCatch(serverLevel, player, hook)) {
                spawnCaughtItem(serverLevel, hook, player, stack);
                awardCatchStat(player, stack);
            }

            serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, hook.getX(), hook.getY() + 0.5, hook.getZ() + 0.5, serverLevel.getRandom().nextInt(6) + 1));
        }

        if (caughtSomething) {
            FishingGearUtil.damageFound(player, lure, 1);
        }
    }

    private static List<ItemStack> rollSecondCatch(ServerLevel level, Player player, FishingHook hook) {
        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, hook.position())
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                .withParameter(LootContextParams.THIS_ENTITY, hook)
                .withParameter(LootContextParams.ATTACKING_ENTITY, hook.getOwner())
                .withLuck(player.getLuck())
                .create(LootContextParamSets.FISHING);
        LootTable table = level.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
        return table.getRandomItems(params);
    }

    private static void spawnCaughtItem(ServerLevel level, FishingHook hook, Player player, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level, hook.getX(), hook.getY(), hook.getZ(), stack);
        double dx = player.getX() - itemEntity.getX();
        double dy = player.getY() - itemEntity.getY();
        double dz = player.getZ() - itemEntity.getZ();
        itemEntity.setDeltaMovement(dx * 0.1, dy * 0.1 + Math.sqrt(Math.sqrt(dx * dx + dy * dy + dz * dz)) * 0.08, dz * 0.1);
        level.addFreshEntity(itemEntity);
    }

    private static void awardCatchStat(Player player, ItemStack stack) {
        if (stack.is(ItemTags.FISHES)) {
            player.awardStat(Stats.FISH_CAUGHT, 1);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        AttributeInstance luck = player.getAttribute(Attributes.LUCK);
        if (luck == null) {
            return;
        }

        FishingGearUtil.FoundStack lure = FishingGearUtil.findFirstMatching(player, CDModTags.FISHING_LURE);
        boolean hasShiny = lure != null && lure.stack().getItem() instanceof LureItem lureItem && lureItem.getEffect() == LureEffect.SHINY;
        boolean hasModifier = luck.getModifier(SHINY_LUCK_ID) != null;

        if (hasShiny && !hasModifier) {
            luck.addTransientModifier(new AttributeModifier(SHINY_LUCK_ID, SHINY_LUCK_BONUS, AttributeModifier.Operation.ADD_VALUE));
        } else if (!hasShiny && hasModifier) {
            luck.removeModifier(SHINY_LUCK_ID);
        }
    }
}