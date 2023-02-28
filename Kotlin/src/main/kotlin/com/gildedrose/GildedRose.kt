package com.gildedrose

class GildedRose(
    private val items: List<Item>
) {
    fun updateQuality() {
        for (item in items) {
            item.type().update(item)
        }
    }
}

object sulfuras : ItemType("Sulfuras, Hand of Ragnaros")
object brie : ItemType("Aged Brie")
object passes : ItemType("Backstage passes to a TAFKAL80ETC concert")
object other : ItemType("")


sealed class ItemType(val description: String) {

    fun update(item: Item) {
        item.sellIn -= ageingFor()
        item.degradeBy(degradationFor(item))
    }

    private fun ageingFor() =
        when (this) {
            sulfuras -> 0
            else -> 1
        }

    private fun degradationFor(item: Item) =
        when (this) {
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
}

val types = listOf(sulfuras, brie, passes, other)
fun Item.type() = types.find { it.description == this.name } ?: other

private fun Item.degradeBy(change: Int) {
    val newValue = quality - change
    quality = newValue.coerceIn(0, Math.max(50, quality))
}


