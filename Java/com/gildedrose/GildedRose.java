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

        if (type == ItemType.Brie) {
            item.quality = item.quality + 1;
        } else if (type == ItemType.Pass) {
            item.quality = item.quality + 1;

            if (item.sellIn < 10) {
                item.quality = item.quality + 1;
            }

            if (item.sellIn < 5) {
                item.quality = item.quality + 1;
            }

        } else {
            if (type != ItemType.Sulfuras) {
                item.quality = item.quality - 1;
            }
        }
        if (pastSellBy(item)) {
            if (type == ItemType.Brie) {
                item.quality = item.quality + 1;
            } else {
                if (type == ItemType.Pass) {
                    item.quality = 0;
                } else if (type != ItemType.Sulfuras) {
                    item.quality = item.quality - 1;
                }

            }
        }

        type.saturation.apply(item);
    }

    private boolean pastSellBy(Item item) {
        return item.sellIn < 0;
    }

    enum ItemType {
        Sulfuras(Ageing.NoAgeing, Saturation.NoSaturation),
        Brie(Ageing.StandardAgeing, Saturation.Standard),
        Pass(Ageing.StandardAgeing, Saturation.Standard),
        Other(Ageing.StandardAgeing, Saturation.Standard);

        private final Ageing ageing;
        private final Saturation saturation;

        ItemType(Ageing ageing, Saturation saturation) {
            this.ageing = ageing;
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
