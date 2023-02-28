package com.gildedrose

import com.gildedrose.ItemType.*

class GildedRose(
    private val items: List<Item>
) {
    fun updateQuality() {
        for (item in items) {
            item.type().update(item)
        }
    }
}

private fun ItemType.update(item: Item) {
    item.sellIn -= ageingFor()
    item.degradeBy(degradationFor(item))
}

enum class ItemType(val description: String) {
    sulfuras("Sulfuras, Hand of Ragnaros"),
    brie("Aged Brie"),
    passes("Backstage passes to a TAFKAL80ETC concert"),
    other("")
}

fun Item.type() = ItemType.values().find { it.description == this.name } ?: other


private fun ItemType.ageingFor() =
    when (this) {
        sulfuras -> 0
        else -> 1
    }

private fun ItemType.degradationFor(item: Item) =
    when(this) {
        brie -> if (item.sellIn < 0) -2 else -1
        passes -> when {
            item.sellIn < 0 -> item.quality
            item.sellIn < 5 -> -3
            item.sellIn < 10 -> -2
            else -> -1
        }
        sulfuras -> 0
        other -> if (item.sellIn < 0) 2 else 1
    }



private fun Item.degradeBy(change: Int) {
    val newValue = quality - change
    quality = newValue.coerceIn(0, Math.max(50, quality))
}


