package com.gildedrose;

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
        ItemType type = typeOf(item);

        type.ageing.apply(item);
        type.degradation.apply(item);
        type.saturation.apply(item);
    }

    enum ItemType {
        Sulfuras(Ageing.NoAgeing, Degradation.Sulfuras, Saturation.NoSaturation),
        Brie(Ageing.StandardAgeing, Degradation.BetterWithTime, Saturation.Standard),
        Pass(Ageing.StandardAgeing, Degradation.Pass, Saturation.Standard),
        Other(Ageing.StandardAgeing, Degradation.Standard, Saturation.Standard);

        private final Ageing ageing;
        private final Degradation degradation;
        private final Saturation saturation;

        ItemType(Ageing ageing, Degradation degradation, Saturation saturation) {
            this.ageing = ageing;
            this.degradation = degradation;
            this.saturation = saturation;
        }
    }

    private ItemType typeOf(Item item) {
        String name = item.name;
        if (name.equals("Sulfuras, Hand of Ragnaros"))
            return ItemType.Sulfuras;
        if (name.equals("Aged Brie"))
            return ItemType.Brie;
        if (name.equals("Backstage passes to a TAFKAL80ETC concert"))
            return ItemType.Pass;
        return ItemType.Other;
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
        Sulfuras,
        BetterWithTime {
            public void apply(Item item) {
                item.quality = item.quality + 1;
                if (pastSellBy(item)) {
                    item.quality = item.quality + 1;
                }
            }
        },
        Pass {
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
        Standard {
            @Override
            public void apply(Item item) {
                item.quality = item.quality - 1;
                if (pastSellBy(item))
                    item.quality = item.quality - 1;
            }
        };

        private static boolean pastSellBy(Item item) {
            return item.sellIn < 0;
        }

        public void apply(Item item) {}
    }

    enum Saturation {
        Standard {
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
