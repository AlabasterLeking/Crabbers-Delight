package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.world.BottleMessageEntry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class CDBottleMessages {
    private static ResourceKey<BottleMessageEntry> key(String id) {
        return ResourceKey.create(CDModDatapackRegistries.BOTTLE_MESSAGE_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, id));
    }

    public static void bootstrap(BootstrapContext<BottleMessageEntry> context) {
        context.register(key("crabs_got_me_first"), new BottleMessageEntry("If you're reading this, the crabs got to me first.", "Amelia Earhart", Optional.of("Torn Page"), Optional.empty(), 1));
        context.register(key("day_47_seagulls"), new BottleMessageEntry("Day 47: Still stranded. The seagulls are starting to look friendly.", "Unknown", Optional.of("Castaway Journal Page"), Optional.empty(), 1));
        context.register(key("good_fishing_spot"), new BottleMessageEntry("Whoever finds this - the good fishing spot is not a secret anymore.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("driftwood_raft_bad_idea"), new BottleMessageEntry("I told them building a raft out of driftwood was a bad idea.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("crab_chess"), new BottleMessageEntry("Day 12: still no rescue. Day 13: taught a crab to play chess. I'm losing.", "Unknown", Optional.of("Castaway Journal Page"), Optional.empty(), 1));
        context.register(key("boots_for_coconut"), new BottleMessageEntry("I traded my boots for a coconut. Worst trade of my life. Second worst: the boots.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("ocean_took_dignity"), new BottleMessageEntry("The ocean took my ship, my supplies, and my dignity. It can keep the dignity.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("not_a_creeper"), new BottleMessageEntry("My last words: at least it wasn't a creeper.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("unsinkable_opinion"), new BottleMessageEntry("Note to self: 'unsinkable' is not a raft feature, it's a raft opinion.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("gerald_is_a_jerk"), new BottleMessageEntry("I've named all the local seagulls. Gerald is a jerk.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("made_my_peace"), new BottleMessageEntry("If you found this bottle before finding me, I've made my peace with that.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("day_30_tan"), new BottleMessageEntry("Day 30: The tan is incredible. The circumstances are less so.", "Unknown", Optional.of("Castaway Journal Page"), Optional.empty(), 1));
        context.register(key("not_a_high_bar"), new BottleMessageEntry("Whoever you are, you're doing better than me, and that's not a high bar.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("send_help_oclock"), new BottleMessageEntry("I built a sundial out of driftwood. It's always 'send help o'clock.'", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("more_sand"), new BottleMessageEntry("The good news: I found treasure. The bad news: it's just more sand.", "Unknown", Optional.empty(), Optional.of("minecraft:sand"), 1));
        context.register(key("rating_waves"), new BottleMessageEntry("I've started rating waves out of ten. This one's a two. Rude.", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("jar_of_dirt"), new BottleMessageEntry("I've got a jar of dirt!", "Jack Sparrow", Optional.empty(), Optional.of("minecraft:dirt"), 1));
        context.register(key("try_prospect"), new BottleMessageEntry("Also try Prospect!", "Alabaster", Optional.empty(), Optional.empty(), 1));
        context.register(key("try_hnh"), new BottleMessageEntry("Also try Hearth and Harvest!", "Alabaster", Optional.empty(), Optional.empty(), 1));
        context.register(key("crab_dye"), new BottleMessageEntry("Did you know you can dye the crabs?", "Alabaster", Optional.empty(), Optional.empty(), 1));
        context.register(key("krabby_patty"), new BottleMessageEntry("- Bun\n- Lettuce\n- Tomato\n- Ketchup\n- Mustard\n- Pickles\n- Cheese\n- Crab", "Mr. Krabs", Optional.of("Secret Formula"), Optional.empty(), 1));
        context.register(key("crusoe"), new BottleMessageEntry("Thus fear of danger is ten thousand times more terrifying than danger itself", "Robinson Crusoe", Optional.empty(), Optional.empty(), 1));
        context.register(key("hemingway"), new BottleMessageEntry("Every day is a new day. It is better to be lucky. But I would rather be exact. Then when luck comes you are ready", "Hemingway", Optional.empty(), Optional.empty(), 1));
        context.register(key("future_pirate_king"), new BottleMessageEntry("Still looking for the treasure. Still haven't found a ship. Still have a really good feeling about this.", "A Future Pirate King", Optional.empty(), Optional.empty(), 1));
        context.register(key("coconut_friend"), new BottleMessageEntry("Made a friend out of a coconut. He doesn't talk much, but he's a great listener.", "Chuck", Optional.empty(), Optional.of("crabbersdelight:coconut"), 1));
        context.register(key("three_hour_tour"), new BottleMessageEntry("It was supposed to be a three hour tour. It was not a three hour tour.", "A First Mate", Optional.empty(), Optional.empty(), 1));
        context.register(key("forget_what_looking_for"), new BottleMessageEntry("I was looking for something important. I forget what. I'm sure it'll come back to me.", "Dory", Optional.empty(), Optional.empty(), 1));
        context.register(key("traded_voice_for_legs"), new BottleMessageEntry("Traded my voice for legs once. In retrospect, should have negotiated harder.", "A Mermaid", Optional.empty(), Optional.empty(), 1));
        context.register(key("one_star_ocean"), new BottleMessageEntry("1 star. Extremely wet. Would not recommend for a relaxing afternoon. Do not ask about the sharks.", "A Very Wet Tourist", Optional.empty(), Optional.empty(), 1));
        context.register(key("same_fish_eleven_times"), new BottleMessageEntry("Please stop catching me. I am the same fish every time. We have met eleven times now.", "A Very Tired Fish", Optional.empty(), Optional.of("minecraft:cod"), 1));
        context.register(key("sand"), new BottleMessageEntry("I don't like sand, it's coarse, rough, irritating, and it gets everywhere", "Anakin Skywalker", Optional.empty(), Optional.of("minecraft:sand"), 1));
        context.register(key("rescue_me"), new BottleMessageEntry("If you're reading this, I require rescue. You can find me at-\n[the rest of the note has been rendered illegible through damage]", "Unknown", Optional.empty(), Optional.empty(), 1));
        context.register(key("villager_trade_receipt"), new BottleMessageEntry("= VILLAGE TRADE CO. =\n1x Bread - 1 Emerald\n3x Paper - 1 Emerald\n1x Enchanted Book - 12 Emeralds (ROBBERY)\n1x Glass Bottle - 1 Emerald\n-------------------\nThank you for your business.\nAll sales final", "A Village Merchant", Optional.of("Trade Receipt"), Optional.of("minecraft:emerald"), 1));
        context.register(key("the_police"), new BottleMessageEntry("I'm sending out an S.O.S to the world", "The Police", Optional.empty(), Optional.empty(), 1));
        context.register(key("watching"), new BottleMessageEntry("I will be watching you with great interest, %player%...", "ᒲ||ᓭℸᒷ∷||", Optional.of("Mysterious Note"), Optional.empty(), 1));
        context.register(key("grandmas_letter"), new BottleMessageEntry("Dear %player%, are you eating well?\n\nLove, Grandma", "Grandma", Optional.of("Letter from Grandma"), Optional.of("minecraft:cookie"), 1));

    }
}