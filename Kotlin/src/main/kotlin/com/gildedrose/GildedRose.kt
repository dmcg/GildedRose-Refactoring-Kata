package com.gildedrose

import kotlin.math.max

class GildedRose(
    private val items: List<Item>,
) {
    fun updateQuality() {
        for (item in items) {
            item.type().update(item)
        }
    }
}

fun Item.type() = when (name) {
    "Sulfuras, Hand of Ragnaros" -> Sulfuras
    "Aged Brie" -> Brie
    "Backstage passes to a TAFKAL80ETC concert" -> Passes
    "Conjured Mana Cake" -> ConjuredCake
    else -> Other
}

open class ItemType {

    fun update(item: Item) {
        item.sellIn -= ageing()
        item.quality = (item.quality - degradationFor(item))
            .coerceIn(0, max(50, item.quality))
    }

    protected open fun ageing() = 1

    protected open fun degradationFor(item: Item) = if (item.sellIn < 0) 2 else 1
}

object Sulfuras : ItemType() {
    override fun ageing() = 0
    override fun degradationFor(item: Item) = 0
}

object Brie : ItemType() {
    override fun degradationFor(item: Item) = if (item.sellIn < 0) -2 else -1
}

object Passes : ItemType() {
    override fun degradationFor(item: Item) = when {
        item.sellIn < 0 -> item.quality
        item.sellIn < 5 -> -3
        item.sellIn < 10 -> -2
        else -> -1
    }
}

object ConjuredCake : ItemType() {
    override fun degradationFor(item: Item) = 2 * super.degradationFor(item)
}

object Other : ItemType()