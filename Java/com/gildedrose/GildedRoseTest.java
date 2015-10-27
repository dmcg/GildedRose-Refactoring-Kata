package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void ordinary_items_decrease_in_quality_one_per_day_up_to_expiry() {
        checkOneUpdate("foo", 1, 42, 0, 41);
    }

    @Test
    public void ordinary_items_decrease_in_quality_two_per_day_after_expiry() {
        checkOneUpdate("foo", 0, 42, -1, 40);
    }

    @Test
    public void Aged_Brie_increases_in_quality_up_to_50() {
        checkOneUpdate("Aged Brie", 1, 42, 0, 43);
        checkOneUpdate("Aged Brie", -10, 49, -11, 50);
        checkOneUpdate("Aged Brie", -11, 50, -12, 50);
    }

    @Test
    public void Sulfuras_doesnt_degrade() {
        checkOneUpdate("Sulfuras, Hand of Ragnaros", 1, 80, 1, 80);
    }

    @Test
    public void Backstage_Passes_increase_in_quality_then_decrease() {
        checkOneUpdate("Backstage passes to a TAFKAL80ETC concert", 11, 15, 10, 16);
        checkOneUpdate("Backstage passes to a TAFKAL80ETC concert", 10, 15, 9, 17);
        checkOneUpdate("Backstage passes to a TAFKAL80ETC concert", 5, 15, 4, 18);
        checkOneUpdate("Backstage passes to a TAFKAL80ETC concert", 1, 15, 0, 18);
        checkOneUpdate("Backstage passes to a TAFKAL80ETC concert", 0, 15, -1, 0);
    }

    private void checkOneUpdate(String itemName, int sellIn, int quality, int expectedSellIn, int expectedQuality) {
        Item item = new Item(itemName, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] {item});
        app.updateQuality();
        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }


}
