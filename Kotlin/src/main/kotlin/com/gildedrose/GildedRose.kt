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
            increment()
            if (sellIn < 0) {
                increment()
            }
        }

        "Backstage passes to a TAFKAL80ETC concert" -> {
            if (quality < 50) {
                quality = quality + 1

                if (sellIn < 10) {
                    increment()
                }

                if (sellIn < 5) {
                    increment()
                }
            }
            if (sellIn < 0) {
                quality = 0
            }
        }

        "Sulfuras, Hand of Ragnaros" -> {
        }

        else -> {
            decrement()
            if (sellIn < 0) {
                decrement()
            }
        }
    }
}

private fun Item.decrement() {
    if (quality > 0) {
        quality = quality - 1
    }
}

private fun Item.increment() {
    if (quality < 50) {
        quality = quality + 1
    }
}


