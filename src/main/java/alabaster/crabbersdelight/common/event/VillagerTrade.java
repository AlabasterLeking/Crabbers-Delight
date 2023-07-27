package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.common.Config;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import alabaster.crabbersdelight.CrabbersDelight;

import alabaster.crabbersdelight.common.registry.ModItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mod.EventBusSubscriber(modid = CrabbersDelight.MODID)
@ParametersAreNonnullByDefault
public class VillagerTrade
{
    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {

        if (!Config.FISHERMAN_BUY_SEAFOOD.get()) return;


        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession profession = event.getType();
        ResourceLocation professionKey = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
        if (professionKey == null) return;
        if (professionKey.getPath().equals("fisherman")) {
            trades.get(1).add(emeraldForItemsTrade(ModItems.RAW_CRAB.get(), 6, 16, 2));
            trades.get(1).add(emeraldForItemsTrade(ModItems.RAW_SHRIMP.get(), 8, 16, 2));
            trades.get(2).add(emeraldForItemsTrade(ModItems.RAW_CLAWSTER.get(), 4, 16, 5));
            trades.get(2).add(emeraldForItemsTrade(ModItems.CLAM.get(), 3, 16, 5));
            trades.get(4).add(emeraldForItemsTrade(ModItems.PEARL.get(), 1, 16, 5));
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        if (Config.WANDERING_TRADER_PEARLS.get()) {
            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            trades.add(heartForPearlsTrade(ModItems.PEARL.get(), 32, 4, 12));
            trades.add(spongeForPearlsTrade(ModItems.PEARL.get(), 8, 4, 12));
            trades.add(tridentForPearlsTrade(ModItems.PEARL.get(), 64, 1, 12));
        }
    }

    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing heartForPearlsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.HEART_OF_THE_SEA), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing spongeForPearlsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.SPONGE), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing tridentForPearlsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.TRIDENT), maxTrades, xp, 0.05F);
    }
}