package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    private final GildedRose app = new GildedRose(new Item[0]);

    @Test
    public void ordinary_items_decrease_in_quality_one_per_day_up_to_expiry() {
        checkOneUpdate(item("foo", 1, 42), 0, 41);
    }

    @Test
    public void ordinary_items_decrease_in_quality_two_per_day_after_expiry() {
        checkOneUpdate(item("foo", 0, 42), -1, 40);
    }

    @Test
    public void Aged_Brie_increases_in_quality_up_to_50() {
        checkOneUpdate(item("Aged Brie", 1, 42), 0, 43);
        checkOneUpdate(item("Aged Brie", 0, 43), -1, 45);
        checkOneUpdate(item("Aged Brie", -10, 49), -11, 50);
        checkOneUpdate(item("Aged Brie", -11, 50), -12, 50);
    }

    @Test
    public void Sulfuras_doesnt_degrade() {
        checkOneUpdate(item("Sulfuras, Hand of Ragnaros", 1, 80), 1, 80);
    }

    @Test
    public void Backstage_Passes_increase_in_quality_then_decrease() {
        checkOneUpdate(item("Backstage passes to a TAFKAL80ETC concert", 11, 15), 10, 16);
        checkOneUpdate(item("Backstage passes to a TAFKAL80ETC concert", 10, 15), 9, 17);
        checkOneUpdate(item("Backstage passes to a TAFKAL80ETC concert", 5, 15), 4, 18);
        checkOneUpdate(item("Backstage passes to a TAFKAL80ETC concert", 1, 15), 0, 18);
        checkOneUpdate(item("Backstage passes to a TAFKAL80ETC concert", 0, 15), -1, 0);
    }

    private void checkOneUpdate(Item item, int expectedSellIn, int expectedQuality) {
        app.update(item);
        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }

    private Item item(String itemName, int sellIn, int quality) {
        return new Item(itemName, sellIn, quality);
    }

}
