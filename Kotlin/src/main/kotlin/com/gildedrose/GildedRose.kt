package com.gildedrose

import kotlin.math.max

class GildedRose(
    private val items: List<Item>
) {
    fun updateQuality() {
        for (item in items) {
            item.type().update(item)
        }
    }
}

val types = listOf(Sulfuras, Brie, Passes, ConjuredCake)
fun Item.type() = types.find { it.description == this.name } ?: Other


open class ItemType(val description: String) {

    fun update(item: Item) {
        item.sellIn -= ageing()
        item.quality = (item.quality - degradationFor(item))
            .coerceIn(0, max(50, item.quality))
    }

    protected open fun ageing() = 1

    protected open fun degradationFor(item: Item) = if (item.sellIn < 0) 2 else 1
}


object Sulfuras : ItemType("Sulfuras, Hand of Ragnaros") {
    override fun ageing() = 0
    override fun degradationFor(item: Item) = 0
}

object Brie : ItemType("Aged Brie") {
    override fun degradationFor(item: Item) = if (item.sellIn < 0) -2 else -1
}

object Passes : ItemType("Backstage passes to a TAFKAL80ETC concert") {
    override fun degradationFor(item: Item) = when {
        item.sellIn < 0 -> item.quality
        item.sellIn < 5 -> -3
        item.sellIn < 10 -> -2
        else -> -1
    }
}

object ConjuredCake : ItemType("Conjured Mana Cake") {
    override fun degradationFor(item: Item) = 2 * super.degradationFor(item)
}

object Other : ItemType("")
