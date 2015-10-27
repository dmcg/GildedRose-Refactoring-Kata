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
        String name = item.name;
        ItemType type = typeOf(item);

        type.ageing.apply(item);

        if (name.equals("Aged Brie")) {
            item.quality = item.quality + 1;
        } else if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            item.quality = item.quality + 1;

            if (item.sellIn < 10) {
                item.quality = item.quality + 1;
            }

            if (item.sellIn < 5) {
                item.quality = item.quality + 1;
            }

        } else {
            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                item.quality = item.quality - 1;
            }
        }
        if (pastSellBy(item)) {
            if (name.equals("Aged Brie")) {
                item.quality = item.quality + 1;
            } else {
                if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    item.quality = 0;
                } else if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                    item.quality = item.quality - 1;
                }

            }
        }

        if (item.quality > 50 && !name.equals("Sulfuras, Hand of Ragnaros"))
            item.quality = 50;
        if (item.quality < 0)
            item.quality = 0;
    }

    private boolean pastSellBy(Item item) {
        return item.sellIn < 0;
    }

    enum ItemType {
        Sulfuras(Ageing.NoAgeing),
        Brie(Ageing.StandardAgeing),
        Pass(Ageing.StandardAgeing),
        Other(Ageing.StandardAgeing);

        private final Ageing ageing;

        ItemType(Ageing ageing) {

            this.ageing = ageing;
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
}
