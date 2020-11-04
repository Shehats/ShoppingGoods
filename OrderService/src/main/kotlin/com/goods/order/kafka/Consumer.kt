package com.goods.order.kafka

import com.goods.order.controllers.OfferOrderController
import com.goods.order.gorcery.Item
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*


class Consumer(var items: Map<String, Item>) {
    var kafkaConsumer: KafkaConsumer<String, String>
    var topic = "order-submitted"
    var orderController = OfferOrderController()
    var producer = Producer()

    init {
        var props = Properties()
        val deserializer: String = StringDeserializer::class.java.name
        props["bootstrap.servers"] = "localhost:9092"
        props["group.id"] = "order-consumer"
        props["enable.auto.commit"] = "true"
        props["auto.commit.interval.ms"] = "1000"
        props["auto.offset.reset"] = "earliest"
        props["session.timeout.ms"] = "30000"
        props["key.deserializer"] = deserializer
        props["value.deserializer"] = deserializer
        kafkaConsumer = KafkaConsumer(props)
    }

    fun consume() {
        kafkaConsumer.subscribe(listOf(topic))
        var message = ""
        while (true) {
            var records = kafkaConsumer.poll(1000)
            records.forEach {
                message = it.value()
                if (message.equals("end", true)) {
                    return
                }
                println(String.format("Got order %s from kafka", message))
                producer.produce(orderController.calculate(items, it.value().split(',').filter { line -> line.isNotEmpty() }.map { line -> line.trim().toLowerCase() }))
            }
        }
    }
}