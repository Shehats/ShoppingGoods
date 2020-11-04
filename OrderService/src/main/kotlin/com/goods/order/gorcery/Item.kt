package com.goods.order.gorcery

import com.goods.order.gorcery.offers.Offer

data class Item(val price: Double, var quantity: Int, var offer: Offer? = null)