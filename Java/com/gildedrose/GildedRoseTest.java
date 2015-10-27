package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void ordinary_items_decrease_in_quality_one_per_day_up_to_expiry() {
        checkOneUpdate("foo", 1, 42, 41);
    }

    @Test
    public void ordinary_items_decrease_in_quality_two_per_day_after_expiry() {
        checkOneUpdate("foo", 0, 42, 40);
    }

    @Test
    public void Aged_Brie_increases_in_quality_up_to_50() {
        checkOneUpdate("Aged Brie", 1, 42, 43);
        checkOneUpdate("Aged Brie", -10, 49, 50);
        checkOneUpdate("Aged Brie", -11, 50, 50);
    }

    private void checkOneUpdate(String itemName, int sellIn, int quality, int expectedQuality) {
        Item item = new Item(itemName, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {item});
        app.updateQuality();
        assertEquals(sellIn -1, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }


}
