package com.goods.order

import com.goods.order.controllers.BasicOrderController
import com.goods.order.controllers.OfferOrderController
import com.goods.order.controllers.OrderController
import com.goods.order.gorcery.Item
import com.goods.order.gorcery.offers.AppleOffer
import com.goods.order.gorcery.offers.OrangeOffer
import com.goods.order.kafka.Consumer

class App {
    var items: MutableMap<String, Item>
    init {
        items = mutableMapOf()
        items.put("apple", Item(0.6, 20, AppleOffer()))
        items.put("orange", Item(0.25, 15, OrangeOffer()))
        items.put("banana", Item(0.49, 10))
    }

    fun calculate(inputList: List<String>, mode: String): String {
        var controller: OrderController = when(mode) {
            "basic" -> BasicOrderController()
            else -> OfferOrderController()
        }
        return controller.calculate(items, inputList)
    }

    fun runKafka() {
        var consumer = Consumer(items)
        consumer.consume()
    }
}

fun main() {
    var app = App()
    var input = ""
    var mode = "basic"
    while (true) {
        input = readLine() ?: "end"
        if (input.equals("end", true)) {
            break
        }
        mode = readLine() ?: mode
        var inputList = input.split(',').filter { line -> line.isNotEmpty() }.map { line -> line.trim().toLowerCase() }
        println(app.calculate(inputList, mode))
    }
    println("Now running in kafka mode")
    app.runKafka()
}