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

fun Item.type() = ItemType.values().find { it.description == this.name }
private fun isPasses(item: Item) = item.type() == passes

private fun isBrie(item: Item) = item.type() == brie

private fun isSulfurus(item: Item) = item.type() == sulfuras


private fun ageingFor(item: Item) =
    if (isSulfurus(item)) 0
    else 1

private fun degradationFor(item: Item) =
    if (isBrie(item)) {
        if (item.sellIn < 0) -2 else -1
    } else if (isPasses(item)) when {
        item.sellIn < 0 -> item.quality
        item.sellIn < 5 -> -3
        item.sellIn < 10 -> -2
        else -> -1
    }
    else if (isSulfurus(item)) 0
    else if (item.sellIn < 0) 2 else 1



private fun Item.degradeBy(change: Int) {
    val newValue = quality - change
    quality = newValue.coerceIn(0, Math.max(50, quality))
}


