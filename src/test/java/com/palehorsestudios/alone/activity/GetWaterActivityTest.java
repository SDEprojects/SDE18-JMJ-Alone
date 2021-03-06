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

public class GetWaterActivityTest {

    static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.8;
    static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.6;
    static final double LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 179.2;

    Logger logger = Logger.getLogger(GetWaterActivityTest.class.getName());
    Activity getItemFromShelter;
    Activity getWater;
    Player player;

    @Before
    public void setUp() {
        getWater = GetWaterActivity.getInstance();
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
    public void testGetWaterWithoutItems() {
        int previousWater = player.getShelter().getWaterTank();
        int previousHydration = player.getHydration();
        String getWaterResult = getWater.act(new Choice("get water", player));
        int waterChange = player.getShelter().getWaterTank() - previousWater;
        boolean validWaterChange = false;
        for (int i = 4; i < 7; i++) {
            if (waterChange == i) {
                validWaterChange = true;
                break;
            }
        }
        assertTrue(validWaterChange);
        if (waterChange == 4) {
            assertEquals(16, player.getMorale());
            assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
        } else if (waterChange == 5) {
            assertEquals(16, player.getMorale());
            assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
        } else {
            assertEquals(17, player.getMorale());
            assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
        }
        assertEquals("You added " + waterChange + " in the water tank.", getWaterResult);
    }

    @Test
    public void testGetWaterWithItems() {
        getItemFromShelter.act(new Choice("pot", player, (ItemFactory.getNewInstance("Pot"))));
        int previousWater = player.getShelter().getWaterTank();
        int previousHydration = player.getHydration();
        String getWaterResult = getWater.act(new Choice("get water", player));
        int waterChange = player.getShelter().getWaterTank() - previousWater;
        boolean validWaterChange = false;
        for (int i = 5; i < 8; i++) {
            if (waterChange == i) {
                validWaterChange = true;
                break;
            }
        }
        assertTrue(validWaterChange);
        if (waterChange == 5) {
            assertEquals(16, player.getMorale());
            assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
        } else if (waterChange == 16) {
            assertEquals(6, player.getMorale());
            assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
        } else {
            assertEquals(17, player.getMorale());
            assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
            assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
        }
        assertEquals("You added " + waterChange + " in the water tank.", getWaterResult);
    }
}