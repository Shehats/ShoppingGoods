package com.goods.order.gorcery.offers

// 3 for the price of 2
class OrangeOffer: Offer {
    override fun calculateOfferPrice(quantity: Int, unitPrice: Double): Double {
        var withOffer = 0
        if (quantity >= 3) {
            withOffer = quantity - quantity % 3
        }
        return ((quantity - withOffer) * unitPrice) + ((withOffer * unitPrice * 2)/3)
    }
}