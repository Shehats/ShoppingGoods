package com.goods.order.controllers

import com.goods.order.gorcery.Item

interface OrderController {
    fun calculate(items: Map<String, Item>, inputList: List<String>): String
}