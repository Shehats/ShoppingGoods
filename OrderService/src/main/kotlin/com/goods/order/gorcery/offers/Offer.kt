package com.goods.order.gorcery.offers

interface Offer {
    fun calculateOfferPrice(quantity: Int, unitPrice: Double): Double
}
