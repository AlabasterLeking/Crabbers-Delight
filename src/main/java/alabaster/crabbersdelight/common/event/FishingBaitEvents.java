package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.utils.TackleBoxProximity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

import java.util.List;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishingBaitEvents {
    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        TackleBoxProximity.TackleBoxAccess tackleBox = TackleBoxProximity.find(player);
        if (tackleBox == null) {
            return;
        }

        int baitSlot = TackleBoxItemHandler.BAIT_SLOT_1;
        ItemStack bait = tackleBox.getSlot(baitSlot);
        if (bait.isEmpty()) {
            baitSlot = TackleBoxItemHandler.BAIT_SLOT_2;
            bait = tackleBox.getSlot(baitSlot);
        }
        if (bait.isEmpty()) {
            return;
        }

        ResourceLocation baitId = BuiltInRegistries.ITEM.getKey(bait.getItem());
        ResourceLocation tableId = CrabbersDelight.modPrefix("gameplay/fishing_bait_loot/" + baitId.getPath());

        LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, tableId));
        if (table == LootTable.EMPTY) {
            return;
        }

        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .create(LootContextParamSets.FISHING);

        List<ItemStack> rolled = table.getRandomItems(params);
        event.getDrops().clear();
        event.getDrops().addAll(rolled);

        ItemStack shrunk = bait.copy();
        shrunk.shrink(1);
        tackleBox.setSlot(baitSlot, shrunk);
    }
}