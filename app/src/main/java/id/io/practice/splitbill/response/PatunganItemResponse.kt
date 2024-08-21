package id.io.practice.splitbill.response

import kotlinx.serialization.Serializable

@Serializable
data class PatunganItemResponse (
    val taxFee: String,
    val serviceCharge: String,
    val totalPrice: String,
    val listOrder: List<OrderItem>
)

@Serializable
data class OrderItem(
    val quantity: String,
    val name: String,
    val price: String
)