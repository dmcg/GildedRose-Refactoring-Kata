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
    if (name == "Sulfuras, Hand of Ragnaros") {
    } else {
        sellIn = sellIn - 1
    }

    when (name) {
        "Aged Brie" -> {
            if (quality < 50) {
                quality = quality + 1

            }
        }

        "Backstage passes to a TAFKAL80ETC concert" -> {
            if (quality < 50) {
                quality = quality + 1

                if (sellIn < 10) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }

                if (sellIn < 5) {
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


    if (sellIn < 0) {
        when {
            name == "Aged Brie" -> {
                if (quality < 50) {
                    quality = quality + 1
                }
            }
            name == "Backstage passes to a TAFKAL80ETC concert" -> {
                quality = quality - quality
            }
            name == "Sulfuras, Hand of Ragnaros" -> {
            }
            else -> if (quality > 0) {
                quality = quality - 1
            }
        }
    }
}


