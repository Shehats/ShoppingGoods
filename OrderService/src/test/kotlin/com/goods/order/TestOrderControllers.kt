package com.goods.order

import com.goods.order.controllers.BasicOrderController
import com.goods.order.controllers.OfferOrderController
import com.goods.order.gorcery.Item
import com.goods.order.gorcery.offers.AppleOffer
import com.goods.order.gorcery.offers.OrangeOffer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class TestOrderControllers {
    var basicOrderController = BasicOrderController()
    var offerOrderController = OfferOrderController()

    @Test
    fun testNonOfferSeason() {
        var input = "Apple, Apple, Orange, Apple"
        var items: MutableMap<String, Item> = mutableMapOf()
        items["apple"] = Item(0.6, 20)
        items["orange"] = Item(0.25, 15)
        items["banana"] = Item(0.49, 10)
        assertEquals(basicOrderController.calculate(items, makeList(input)), "The price of your order is $2.05")
    }

    @Test
    fun testOfferSeason() {
        var input = "Apple, Apple, Orange, Apple"
        var items: MutableMap<String, Item> = mutableMapOf()
        items["apple"] = Item(0.6, 20, AppleOffer())
        items["orange"] = Item(0.25, 15, OrangeOffer())
        items["banana"] = Item(0.49, 10)
        assertEquals(offerOrderController.calculate(items, makeList(input)), "The price of your order is $1.45")
        input = "Orange, Orange, Orange, Apple"
        assertEquals(offerOrderController.calculate(items, makeList(input)), "The price of your order is $1.10")
    }

    @Test
    fun testItemNotFound() {
        var input = "Apple, Apple, Orange, Watermelon"
        var items: MutableMap<String, Item> = mutableMapOf()
        items["apple"] = Item(0.6, 20, AppleOffer())
        items["orange"] = Item(0.25, 15, OrangeOffer())
        items["banana"] = Item(0.49, 10)
        assertEquals(offerOrderController.calculate(items, makeList(input)), "Item watermelon is not found")
    }

    @Test
    fun testRunOutOfStock() {
        var input = "Apple, Apple, Orange, Banana, Banana, Banana"
        var items: MutableMap<String, Item> = mutableMapOf()
        items["apple"] = Item(0.6, 20, AppleOffer())
        items["orange"] = Item(0.25, 15, OrangeOffer())
        items["banana"] = Item(0.49, 2)
        assertEquals(offerOrderController.calculate(items, makeList(input)), "Item banana is out of stock")
        assertEquals(basicOrderController.calculate(items, makeList(input)), "Item banana is out of stock")
    }

    private fun makeList(input: String): List<String> {
        return input.split(',').filter { line -> line.isNotEmpty() }.map { line -> line.trim().toLowerCase() }
    }

}