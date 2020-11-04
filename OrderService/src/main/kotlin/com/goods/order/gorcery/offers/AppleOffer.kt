package com.goods.order.gorcery.offers

// Buy one get one free
class AppleOffer:Offer {
    override fun calculateOfferPrice(quantity: Int, unitPrice: Double): Double {
        var withOffer = 0
        if (quantity >= 2) {
            withOffer = quantity - quantity % 2
        }
        return ((quantity - withOffer) * unitPrice) + ((withOffer * unitPrice)/2)
    }
}