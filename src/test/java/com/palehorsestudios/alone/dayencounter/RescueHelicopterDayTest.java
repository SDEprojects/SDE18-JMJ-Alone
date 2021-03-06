package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Items.ItemFactory;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RescueHelicopterDayTest {

    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player(ItemFactory.getNewInstances(
                "Axe",
                "Knife",
                "Fishing Line",
                "Fishing Hooks",
                "Wire",
                "Harmonica",
                "Flint and Steel",
                "Flare",
                "First Aid Kit",
                "Cold Weather Gear"));
    }

    @Test
    public void testEncounterNoFlare() {
        int previousMorale = player.getMorale();
        assertEquals("You hear a helicopter approaching your position rapidly from the north."
                        + " You wave your arms and scream your lungs out, but it is no use."
                        + " They continue flying south and pass your position half a mile to west."
                        + " Perhaps if you had a flare they would have seen it.",
                RescueHelicopterDay.getInstance().encounter(player));
        assertEquals(previousMorale - 5, player.getMorale());
    }

    @Test
    public void testEncounterWithFlare() {
        GetItemActivity.getInstance().act(new Choice("get", player, ItemFactory.getNewInstance("Flare")));
        assertEquals("You hear a helicopter approaching your position rapidly from the north."
                        + " You ignite your flare and wave it wildly over your head."
                        + " Incredibly, they spot your flare and land on a nearby beach."
                        + " They greet you with a warm blanket and tell you to hop in."
                        + " You are saved, but you will never forget this incredible experience.",
                RescueHelicopterDay.getInstance().encounter(player));
        assertTrue(player.isRescued());
    }
}