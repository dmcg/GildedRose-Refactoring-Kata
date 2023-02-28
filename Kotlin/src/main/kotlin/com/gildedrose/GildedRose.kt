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
    age()
    degrade()
}

private fun Item.degrade() {
    when (name) {
        "Aged Brie" -> {
            degradeBy(if (sellIn < 0) -2 else -1)
        }

        "Backstage passes to a TAFKAL80ETC concert" -> {
            degradeBy(
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
            degradeBy(
                if (sellIn < 0) 2 else 1
            )
        }
    }
}

private fun Item.age() {
    when (name) {
        "Sulfuras, Hand of Ragnaros" -> {}
        else -> sellIn = sellIn - 1
    }
}

private fun Item.degradeBy(change: Int) {
    val newValue = quality - change
    quality = newValue.coerceIn(0, 50)
}


