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

object sulfuras : ItemType("Sulfuras, Hand of Ragnaros") {
    override fun ageingFor() = 0
    override fun degradationFor(item: Item) = 0
}

object brie : ItemType("Aged Brie") {
    override fun degradationFor(item: Item) = if (item.sellIn < 0) -2 else -1
}

object passes : ItemType("Backstage passes to a TAFKAL80ETC concert") {
    override fun degradationFor(item: Item) = when {
        item.sellIn < 0 -> item.quality
        item.sellIn < 5 -> -3
        item.sellIn < 10 -> -2
        else -> -1
    }
}

object other : ItemType("")

sealed class ItemType(val description: String) {

    fun update(item: Item) {
        item.sellIn -= ageingFor()
        item.degradeBy(degradationFor(item))
    }

    protected open fun ageingFor() = 1

    protected open fun degradationFor(item: Item) = if (item.sellIn < 0) 2 else 1
}

val types = listOf(sulfuras, brie, passes, other)
fun Item.type() = types.find { it.description == this.name } ?: other

private fun Item.degradeBy(change: Int) {
    val newValue = quality - change
    quality = newValue.coerceIn(0, Math.max(50, quality))
}


