package com.goods.order.controllers

import com.goods.order.gorcery.Item

class BasicOrderController : OrderController {
    override fun calculate(items: Map<String, Item>, inputList: List<String>): String {
        var sum = 0.0
        inputList.forEach {
            if (!items.containsKey(it)) {
                return String.format("Item %s is not found", it)
            }

            if (items[it]?.quantity == 0) {
                return String.format("Item %s is out of stock", it)
            }
            items.getValue(it).quantity --
            sum += items.getValue(it).price
        }
        return String.format("The price of your order is $%.2f", sum)
    }
}