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
            degrade(if (sellIn < 0) -2 else -1)
        }

        "Backstage passes to a TAFKAL80ETC concert" -> {
            degrade(
                when {
                    sellIn < 0 -> quality
                    sellIn < 5 -> -3
                    sellIn < 10 -> -2
                    else -> -1
                }
            )
        }

        "Sulfuras, Hand of Ragnaros" -> {
        }

        else -> {
            degrade(
                if (sellIn < 0) 2 else 1
            )
        }
    }
}

private fun Item.degrade(i: Int) {
    val newValue = quality - i
    quality = newValue.coerceIn(0, 50)
}


