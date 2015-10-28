package com.gildedrose;

import static com.gildedrose.GildedRose.Ageing.NoAgeing;
import static com.gildedrose.GildedRose.Ageing.StandardAgeing;
import static com.gildedrose.GildedRose.Degradation.*;
import static com.gildedrose.GildedRose.Saturation.NoSaturation;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            update(item);
        }
    }

    void update(Item item) {
        typeOf(item).update(item);
    }

    private ItemType typeOf(Item item) {
        String name = item.name;
        if (name.equals("Sulfuras, Hand of Ragnaros"))
            return ItemType.Sulfuras;
        if (name.equals("Aged Brie"))
            return ItemType.Brie;
        if (name.equals("Backstage passes to a TAFKAL80ETC concert"))
            return ItemType.Pass;
        if (name.startsWith("Conjured"))
            return ItemType.Conjured;
        return ItemType.Other;
    }

    enum ItemType {
        Sulfuras(NoAgeing, NoDegradation, NoSaturation),
        Brie(StandardAgeing, BetterWithTime, Saturation.StandardSaturation),
        Pass(StandardAgeing, PassDegradation, Saturation.StandardSaturation),
        Conjured(StandardAgeing, ConjuredDegragation, Saturation.StandardSaturation),
        Other(StandardAgeing, StandardSaturation, Saturation.StandardSaturation);

        public void update(Item item) {
            ageing.apply(item);
            degradation.apply(item);
            saturation.apply(item);
        }

        private final Ageing ageing;
        private final Degradation degradation;
        private final Saturation saturation;

        ItemType(Ageing ageing, Degradation degradation, Saturation saturation) {
            this.ageing = ageing;
            this.degradation = degradation;
            this.saturation = saturation;
        }
    }

    enum Ageing {
        StandardAgeing(1),
        NoAgeing(0);

        public void apply(Item item) {
            item.sellIn = item.sellIn - sellInChangePerDay;
        }

        private final int sellInChangePerDay;

        Ageing(int sellInChangePerDay) {
            this.sellInChangePerDay = sellInChangePerDay;
        }
    }

    enum Degradation {
        NoDegradation,
        BetterWithTime {
            public void apply(Item item) {
                item.quality = item.quality + 1;
                if (pastSellBy(item)) {
                    item.quality = item.quality + 1;
                }
            }
        },
        PassDegradation {
            public void apply(Item item) {
                item.quality = item.quality + 1;

                if (item.sellIn < 10) {
                    item.quality = item.quality + 1;
                }

                if (item.sellIn < 5) {
                    item.quality = item.quality + 1;
                }

                if (pastSellBy(item))
                    item.quality = 0;
            }
        },
        StandardSaturation {
            public void apply(Item item) {
                item.quality = item.quality - 1;
                if (pastSellBy(item))
                    item.quality = item.quality - 1;
            }
        },
        ConjuredDegragation {
            public void apply(Item item) {
                item.quality = item.quality - 2;
                if (pastSellBy(item))
                    item.quality = item.quality - 2;
            }

        };

        private static boolean pastSellBy(Item item) {
            return item.sellIn < 0;
        }

        public void apply(Item item) {}
    }

    enum Saturation {
        StandardSaturation {
            public void apply(Item item) {
                if (item.quality > 50)
                    item.quality = 50;
                if (item.quality < 0)
                    item.quality = 0;
            }
        },
        NoSaturation;

        public void apply(Item item) {}
    }
}
