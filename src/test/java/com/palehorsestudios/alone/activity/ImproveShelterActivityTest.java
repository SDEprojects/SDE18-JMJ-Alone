package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Foods.FoodFactory;
import com.palehorsestudios.alone.Items.ItemFactory;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.player.SuccessRate;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImproveShelterActivityTest {

    static final double HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 178.5;
    static final double HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 177.1;
    static final double HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 174.5;

    Logger logger = Logger.getLogger(ImproveShelterActivityTest.class.getName());
    Activity getItemFromShelter;
    Activity improveShelter;
    Player player;

    @Before
    public void setUp() {
        improveShelter = ImproveShelterActivity.getInstance();
        getItemFromShelter = GetItemActivity.getInstance();
        player = new Player(ItemFactory.getNewInstances(
                "Axe",
                "Knife",
                "Fishing Line",
                "Fishing Hooks",
                "Wire",
                "Harmonica",
                "Flint and Steel",
                "Pot",
                "First Aid Kit",
                "Cold Weather Gear"
        ));
        player.getShelter().addFoodToCache(FoodFactory.getNewInstance("Fish"), 1000);
        player.getShelter().addFoodToCache(FoodFactory.getNewInstance("Squirrel"), 1000);
        player.getShelter().addFoodToCache(FoodFactory.getNewInstance("Rabbit"), 1000);
        player.getShelter().addFoodToCache(FoodFactory.getNewInstance("Porcupine"), 1000);
        player.getShelter().addFoodToCache(FoodFactory.getNewInstance("Moose"), 1000);
    }

    @Test
    public void testImproveShelterNoItems() {
        double previousIntegrity = player.getShelter().getIntegrity();
        int previousHydration = player.getHydration();
        String shelterImprovementResult = improveShelter.act(new Choice("improve shelter", player));
        String[] possibleResults = new String[]{"Slowly but surely, you continue to improve on some semblance of a shelter.",
                "You have a new idea on a way to improve your shelter. You're confident that it will be more comfortable now.",
                "Your shelter is coming along nicely, with several improvements you were able to implement."};
        boolean validResult = false;
        for (String possibleResult : possibleResults) {
            if (shelterImprovementResult.equals(possibleResult)) {
                validResult = true;
                break;
            }
        }
        assertTrue(validResult);
        double shelterIntegrityChange = player.getShelter().getIntegrity() - previousIntegrity;
        if (shelterIntegrityChange < 2) {
            assertEquals(4.0, player.getShelter().getIntegrity(), 0.001);
            assertEquals(6, player.getMorale());
            assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
        } else if (shelterIntegrityChange < 3) {
            assertEquals(5.0, player.getShelter().getIntegrity(), 0.001);
            assertEquals(6, player.getMorale());
            assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
        } else {
            assertEquals(6.0, player.getShelter().getIntegrity(), 0.001);
            assertEquals(7, player.getMorale());
            assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
        }
    }

    @Test
    public void testImproveShelterWithItems() {
        getItemFromShelter.act(new Choice("axe", player, (ItemFactory.getNewInstance("Axe"))));
        double previousIntegrity = player.getShelter().getIntegrity();
        int previousHydration = player.getHydration();
        String shelterImprovementResult = improveShelter.act(new Choice("improve shelter", player));
        String[] possibleResults = new String[]{"Slowly but surely, you continue to improve on some semblance of a shelter.",
                "You have a new idea on a way to improve your shelter. You're confident that it will be more comfortable now.",
                "Your shelter is coming along nicely, with several improvements you were able to implement."};
        boolean validResult = false;
        for (String possibleResult : possibleResults) {
            if (shelterImprovementResult.equals(possibleResult)) {
                validResult = true;
                break;
            }
        }
        assertTrue(validResult);
        double shelterIntegrityChange = player.getShelter().getIntegrity() - previousIntegrity;
        if (shelterIntegrityChange < 2) {
            assertEquals(4.1, player.getShelter().getIntegrity(), 0.001);
            assertEquals(6, player.getMorale());
            assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
        } else if (shelterIntegrityChange < 3) {
            assertEquals(5.2, player.getShelter().getIntegrity(), 0.001);
            assertEquals(7, player.getMorale());
            assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
        } else {
            assertEquals(6.3, player.getShelter().getIntegrity(), 0.001);
            assertEquals(7, player.getMorale());
            assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
        }
    }
}