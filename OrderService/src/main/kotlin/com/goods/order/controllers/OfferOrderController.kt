package com.goods.order.controllers

import com.goods.order.gorcery.Item

class OfferOrderController: OrderController {
    override fun calculate(items: Map<String, Item>, inputList: List<String>): String {
        var orderItems: MutableMap<String, Int> = mutableMapOf()
        inputList.forEach {
            if (!items.containsKey(it)) {
                return String.format("Item %s is not found", it)
            }
            if (items[it]?.quantity == 0) {
                return String.format("Item %s is out of stock", it)
            }
            items.getValue(it).quantity --
            orderItems.putIfAbsent(it, 0)
            orderItems.put(it, orderItems.getValue(it) + 1)
        }
        var sum= 0.0
        orderItems.forEach { (item, quantity) ->
            sum += if (items[item]?.offer != null) {
                items.getValue(item).offer!!.calculateOfferPrice(quantity, items.getValue(item).price)
            } else {
                quantity * items.getValue(item).price
            }
        }
        return String.format("The price of your order is $%.2f", sum)
    }
}