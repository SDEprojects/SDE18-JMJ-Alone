package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.Items.Item;
import com.palehorsestudios.alone.Items.ItemFactory;
import com.palehorsestudios.alone.dayencounter.DayEncounter;
import com.palehorsestudios.alone.gui.model.PlayerStatus;
import com.palehorsestudios.alone.gui.model.Status;
import com.palehorsestudios.alone.player.Player;

public class RainStormDay extends DayEncounter {
    private static DayEncounter encounter;

    private RainStormDay() {
    }

    public static DayEncounter getInstance() {
        if (encounter == null) {
            encounter = new RainStormDay();
        }
        return encounter;
    }

    @Override
    public String encounter(Player player) {

        Item bag = ItemFactory.getNewInstance("Waterproof Bag");
        Item tarp = ItemFactory.getNewInstance("Tarp");
        Item manual = ItemFactory.getNewInstance("Survival Manual");
        Item chord = ItemFactory.getNewInstance("Parachute Chord");

        if (player.getItems().contains(bag)) {
            player.updateWeight(-100);
            player.updateMorale(+2);
            // description if the player has waterproof bag
            // to protect his gear while foraging during the storm
            return "The sky has been swallowed by a rushing thundercloud. " +
                    "The sky darkened and for a brief moment raindrops fell on the woods." +
                    "The storm came closer. A flash of light illuminated the sky and the rain" +
                    "increased significantly. Previously only a few drops had fallen down here and" +
                    "there. Now the rain pelted down so vehemently that you could no longer" +
                    "see in front of you. The rain is getting stronger, and with those flashes," +
                    "you become drenched. Fortunately you have waterproof to protect your critical" +
                    "inventory items. However this rain is taking its toll on your morale." +
                    "You should get to shelter quickly. Its not good to sit under a these trees.";
        } else if (player.getShelter().getEquipment().containsKey(tarp)
                && player.getShelter().getEquipment().containsKey(manual)
                && player.getShelter().getEquipment().containsKey(chord)) {

            // description if the player keeps equipment dry with Tarp
            // and survival knowledge at shelter
            player.updateMorale(-1);
            return "The sky has been swallowed by a rushing thundercloud. " +
                    "The sky darkened and for a brief moment raindrops fell on the woods." +
                    "The storm came closer. A flash of light illuminated the sky and the rain" +
                    "increased significantly. Previously only a few drops had fallen down here and" +
                    "there. Now the rain pelted down so vehemently that you could no longer" +
                    "see in front of you. The rain is getting stronger, and with those flashes," +
                    "you become drenched. You get back to your shelter and you are able to fashion protection" +
                    "for your equipment from the storm.";
        } else {
            player.updateMorale(-4);
            player.getItems().clear();
            // description for if the player does not have anything to shelter his items outside his shelter

            return "The sky has been swallowed by a rushing thundercloud. " +
                    "The sky darkened and for a brief moment raindrops fell on the woods." +
                    "The storm came closer. A flash of light illuminated the sky and the rain" +
                    "increased significantly. Previously only a few drops had fallen down here and" +
                    "there. Now the rain pelted down so vehemently that you could no longer" +
                    "see in front of you. The rain is getting stronger, and with those flashes," +
                    "you become drenched. All of your items washed away.";
        }
    }
}
