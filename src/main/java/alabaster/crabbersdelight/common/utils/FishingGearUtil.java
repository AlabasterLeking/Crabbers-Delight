package alabaster.crabbersdelight.common.utils;

import alabaster.crabbersdelight.common.item.component.TackleBoxContents;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FishingGearUtil {
    public record FoundStack(ItemStack stack, GearSource source, int slot) {}

    public enum GearSource {
        HOTBAR, OFFHAND, TACKLE_BOX
    }

    public static FoundStack findFirstMatching(Player player, TagKey<Item> tag) {
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(tag)) {
                return new FoundStack(stack, GearSource.HOTBAR, slot);
            }
        }

        ItemStack offhand = player.getOffhandItem();
        if (offhand.is(tag)) {
            return new FoundStack(offhand, GearSource.OFFHAND, -1);
        }

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            FoundStack inBox = findInTackleBox(stack, tag);
            if (inBox != null) {
                return inBox;
            }
        }

        FoundStack inOffhandBox = findInTackleBox(offhand, tag);
        return inOffhandBox;
    }

    private static FoundStack findInTackleBox(ItemStack stack, TagKey<Item> tag) {
        TackleBoxContents contents = stack.get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
        if (contents == null) {
            return null;
        }
        for (int i = 0; i < contents.items().size(); i++) {
            ItemStack candidate = contents.items().get(i);
            if (candidate.is(tag)) {
                return new FoundStack(candidate, GearSource.TACKLE_BOX, i);
            }
        }
        return null;
    }

    public static void shrinkFound(Player player, FoundStack found) {
        switch (found.source()) {
            case HOTBAR -> player.getInventory().getItem(found.slot()).shrink(1);
            case OFFHAND -> player.getOffhandItem().shrink(1);
            case TACKLE_BOX -> shrinkInTackleBoxHolder(player, found);
        }
    }

    public static void damageFound(Player player, FoundStack found, int amount) {
        switch (found.source()) {
            case HOTBAR ->
                    player.getInventory().getItem(found.slot()).hurtAndBreak(amount, player, EquipmentSlot.MAINHAND);
            case OFFHAND -> player.getOffhandItem().hurtAndBreak(amount, player, EquipmentSlot.OFFHAND);
            case TACKLE_BOX -> damageInTackleBoxHolder(player, found, amount);
        }
    }

    private static void damageInTackleBoxHolder(Player player, FoundStack found, int amount) {
        for (int slot = 0; slot < 9; slot++) {
            if (damageIfHolds(player, player.getInventory().getItem(slot), found, amount)) {
                return;
            }
        }
        damageIfHolds(player, player.getOffhandItem(), found, amount);
    }

    private static boolean damageIfHolds(Player player, ItemStack holder, FoundStack found, int amount) {
        TackleBoxContents contents = holder.get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
        if (contents == null || found.slot() < 0 || found.slot() >= contents.items().size()) {
            return false;
        }
        ItemStack inSlot = contents.items().get(found.slot());
        if (!ItemStack.matches(inSlot, found.stack())) {
            return false;
        }
        inSlot.hurtAndBreak(amount, player, EquipmentSlot.MAINHAND);
        holder.set(CDModDataComponents.TACKLE_BOX_CONTENTS.get(), new TackleBoxContents(contents.items()));
        return true;
    }

    private static void shrinkInTackleBoxHolder(Player player, FoundStack found) {
        for (int slot = 0; slot < 9; slot++) {
            if (shrinkIfHolds(player.getInventory().getItem(slot), found)) {
                return;
            }
        }
        shrinkIfHolds(player.getOffhandItem(), found);
    }

    private static boolean shrinkIfHolds(ItemStack holder, FoundStack found) {
        TackleBoxContents contents = holder.get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
        if (contents == null || found.slot() < 0 || found.slot() >= contents.items().size()) {
            return false;
        }
        ItemStack inSlot = contents.items().get(found.slot());
        if (!ItemStack.matches(inSlot, found.stack())) {
            return false;
        }
        inSlot.shrink(1);
        holder.set(CDModDataComponents.TACKLE_BOX_CONTENTS.get(), new TackleBoxContents(contents.items()));
        return true;
    }
}