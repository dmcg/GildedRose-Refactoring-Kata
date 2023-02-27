package com.gildedrose

class GildedRose(
    private val items: List<Item>
) {

    fun updateQuality() {
        for (item in items) {
            item.update()
        }
    }
}

private fun Item.update() {
    when (name) {
        "Aged Brie" -> {
            if (quality < 50) {
                quality = quality + 1

            }
        }
        "Backstage passes to a TAFKAL80ETC concert" -> {
            if (quality < 50) {
                quality = quality + 1

                if (sellIn < 11) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }

                if (sellIn < 6) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }
            }
        }
        "Sulfuras, Hand of Ragnaros" -> {
        }
        else -> {
            if (quality > 0) {
                quality = quality - 1
            }
        }
    }

    if (name == "Sulfuras, Hand of Ragnaros") {
    } else {
        sellIn = sellIn - 1
    }

    if (sellIn < 0) {
        if (name == "Aged Brie") {
            if (quality < 50) {
                quality = quality + 1
            }
        } else {
            if (name == "Backstage passes to a TAFKAL80ETC concert") {
                quality = quality - quality
            } else {
                if (quality > 0) {
                    if (name == "Sulfuras, Hand of Ragnaros") return
                    quality = quality - 1
                }
            }
        }
    }
}


