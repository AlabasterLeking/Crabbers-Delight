package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModDatapackRegistries;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.world.BottleMessageEntry;
import alabaster.crabbersdelight.common.world.BottledNoteSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.Optional;

public class MessageBottleItem extends Item {
    private static final float TREASURE_MAP_CHANCE = 0.1f;

    public MessageBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.crabbersdelight.message_bottle.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isShiftKeyDown()) {
            return InteractionResultHolder.pass(stack);
        }
        if (level instanceof ServerLevel serverLevel) {
            openBottle(serverLevel, player, stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private void openBottle(ServerLevel level, Player player, ItemStack bottleStack) {
        RandomSource random = level.getRandom();
        ItemStack result;
        ItemStack bonusItem = ItemStack.EMPTY;

        BottledNoteSavedData savedData = BottledNoteSavedData.get(level);

        if (random.nextFloat() < TREASURE_MAP_CHANCE) {
            result = createTreasureMap(level, player);
        } else {
            Registry<BottleMessageEntry> registry = level.registryAccess().registryOrThrow(CDModDatapackRegistries.BOTTLE_MESSAGE_REGISTRY_KEY);
            List<BottleMessageEntry> flavorEntries = registry.stream().toList();
            List<BottledNoteSavedData.Entry> playerEntries = savedData.asList();
            int totalCount = flavorEntries.size() + playerEntries.size();

            if (totalCount == 0) {
                result = signedNote("", "...", "Unknown", SignedNoteContent.GENERATION_ORIGINAL);
            } else {
                int index = random.nextInt(totalCount);
                if (index < playerEntries.size()) {
                    BottledNoteSavedData.Entry playerNote = playerEntries.get(index);
                    result = signedNote(playerNote.title(), playerNote.text(), playerNote.author(), playerNote.generation());
                    if (!playerNote.reward().isEmpty()) {
                        bonusItem = playerNote.reward().copy();
                    }
                    savedData.remove(playerNote);
                } else {
                    BottleMessageEntry message = flavorEntries.get(index - playerEntries.size());
                    String personalizedText = message.text().replace("%player%", player.getName().getString());
                    result = signedNote(message.title().orElse(""), personalizedText, message.author(), SignedNoteContent.GENERATION_ORIGINAL);
                    bonusItem = resolveRewardItem(message);
                }
            }
        }

        bottleStack.shrink(1);
        ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
        if (!player.getInventory().add(emptyBottle)) {
            player.drop(emptyBottle, false);
        }
        if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }
        if (!bonusItem.isEmpty() && !player.getInventory().add(bonusItem)) {
            player.drop(bonusItem, false);
        }
        level.playSound(null, player.blockPosition(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1f, 1f);
    }

    private ItemStack resolveRewardItem(BottleMessageEntry message) {
        Optional<String> rewardId = message.rewardItem();
        if (rewardId.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ResourceLocation loc = ResourceLocation.tryParse(rewardId.get());
        if (loc == null) {
            return ItemStack.EMPTY;
        }
        Item item = BuiltInRegistries.ITEM.getOptional(loc).orElse(null);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, Math.max(1, message.rewardCount()));
    }

    private ItemStack signedNote(String title, String text, String author, int generation) {
        ItemStack stack = new ItemStack(CDModItems.SIGNED_NOTE.get());
        stack.set(CDModDataComponents.SIGNED_NOTE_CONTENT.get(), new SignedNoteContent(title, text, author, generation));
        return stack;
    }

    private ItemStack createTreasureMap(ServerLevel level, Player player) {
        ItemStack map = new ItemStack(Items.MAP);
        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .create(LootContextParamSets.CHEST);
        LootContext context = new LootContext.Builder(params).create(Optional.empty());
        LootItemFunction explorationMap = ExplorationMapFunction.makeExplorationMap().build();
        return explorationMap.apply(map, context);
    }
}