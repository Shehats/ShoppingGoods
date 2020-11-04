package com.goods.order.kafka
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*


class Consumer {
    var kafkaConsumer: KafkaConsumer<String, String>
    var topic = "order-status"
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
        kafkaConsumer.subscribe(listOf(topic))
    }

    fun consume() {
        var records = kafkaConsumer.poll(1000)
        records.forEach {
            println(it.value())
        }
    }
}