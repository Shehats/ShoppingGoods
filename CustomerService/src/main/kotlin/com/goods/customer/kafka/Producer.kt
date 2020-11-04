package com.goods.order.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*


class Producer {
    var kafkaProducer: KafkaProducer<String, String>
    var topic = "order-submitted"
    init {
        var props = Properties()
        val serializer: String = StringSerializer::class.java.name
        props["bootstrap.servers"] = "localhost:9092"
        props["group.id"] = "order-producer"
        props["key.serializer"] = serializer
        props["value.serializer"] = serializer
        kafkaProducer = KafkaProducer(props)
    }

    fun produce(message: String) {
        kafkaProducer.send(ProducerRecord(topic, UUID.randomUUID().toString(), message))
    }
}