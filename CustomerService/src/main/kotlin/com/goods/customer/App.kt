package com.goods.customer

import com.goods.order.kafka.Consumer
import com.goods.order.kafka.Producer

fun main() {
    var consumer = Consumer()
    var producer = Producer()
    var input = ""
    val thread = Thread {
        while (true) {
            consumer.consume()
        }
        Thread.sleep(1000)
    }

    thread.start()
    while (true) {
        input = readLine() ?: "end"
        if (input.equals("end", true)) {
            break
        }
       producer.produce(input)
    }
}