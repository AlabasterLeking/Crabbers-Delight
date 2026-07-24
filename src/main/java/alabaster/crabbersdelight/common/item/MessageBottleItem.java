package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.world.BottledNoteSavedData;
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
    private static final List<String> FLAVOR_TEXTS = List.of(
            "If you're reading this, the crabs got to me first.",
            "Day 47 stranded. The seagulls are starting to look friendly.",
            "Whoever finds this - the good fishing spot is not a secret anymore.",
            "I told them building a raft out of driftwood was a bad idea.",
            "Day 12: still no rescue. Day 13: taught a crab to play chess. I'm losing.",
            "I traded my boots for a coconut. Worst trade of my life. Second worst: the boots.",
            "The ocean took my ship, my supplies, and my dignity. It can keep the dignity.",
            "My last words: at least it wasn't a creeper.",
            "Note to self: 'unsinkable' is not a raft feature, it's a raft opinion.",
            "I've named all the local seagulls. Gerald is a jerk.",
            "If you found this bottle before finding me, I've made my peace with that.",
            "Day 30. The tan is incredible. The circumstances are less so.",
            "Whoever you are, you're doing better than me, and that's not a high bar.",
            "I built a sundial out of driftwood. It's always 'send help o'clock.'",
            "The good news: I found treasure. The bad news: it's just more sand.",
            "I've started rating waves out of ten. This one's a two. Rude."
    );

    private static final float TREASURE_MAP_CHANCE = 0.1f;

    public MessageBottleItem(Properties properties) {
        super(properties);
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

        BottledNoteSavedData.Entry playerNote = BottledNoteSavedData.get(level).pollWeighted(random);

        if (playerNote != null) {
            result = signedNote(playerNote.title(), playerNote.text(), playerNote.author(), playerNote.generation());
        } else if (random.nextFloat() < TREASURE_MAP_CHANCE) {
            result = createTreasureMap(level, player);
        } else {
            String text = FLAVOR_TEXTS.get(random.nextInt(FLAVOR_TEXTS.size()));
            result = signedNote("", text, "Unknown", SignedNoteContent.GENERATION_ORIGINAL);
        }

        bottleStack.shrink(1);
        if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }
        level.playSound(null, player.blockPosition(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1f, 1f);
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