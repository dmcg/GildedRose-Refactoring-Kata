package com.gildedrose

import com.gildedrose.ItemType.*

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
    sellIn -= ageingFor(this)
    degradeBy(degradationFor(this))
}

enum class ItemType(val description: String) {
    sulfuras("Sulfuras, Hand of Ragnaros"),
    brie("Aged Brie"),
    passes("Backstage passes to a TAFKAL80ETC concert"),
}

private fun ageingFor(item: Item) =
    when (item.name) {
        sulfuras.description -> 0
        else -> 1
    }

private fun degradationFor(item: Item) =
    when (item.name) {
        brie.description -> if (item.sellIn < 0) -2 else -1
        passes.description -> when {
            item.sellIn < 0 -> item.quality
            item.sellIn < 5 -> -3
            item.sellIn < 10 -> -2
            else -> -1
        }

        sulfuras.description -> 0
        else -> if (item.sellIn < 0) 2 else 1
    }


private fun Item.degradeBy(change: Int) {
    val newValue = quality - change
    quality = newValue.coerceIn(0, Math.max(50, quality))
}


