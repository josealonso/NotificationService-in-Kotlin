

import com.josealonso.consumer.entity.NotificationType
import com.josealonso.consumer.entity.OrderStatus
import com.josealonso.consumer.entity.OrderDTO
import com.josealonso.consumer.entity.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.test.EmbeddedKafkaBroker
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(
    properties = [
        "spring.kafka.producer.bootstrap-servers=localhost:3392",
        "spring.kafka.consumer.bootstrap-servers=localhost:3392",
        "app.topic.consumer=test-topic",
        "app.topic.producer=test-topic"
    ]
)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:3392", "port=3392"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EmbeddedKafkaIntegrationTest {

    @Autowired
    private lateinit var embeddedKafka: EmbeddedKafkaBroker

    @Autowired
    private lateinit var producer: KafkaTemplate<String, OrderDTO>

    val orderDtoExample = OrderDTO(
        orderId = 1L,
        name = "order 1",
        price = BigDecimal.ONE,
        status = OrderStatus.PENDING,
        userId = UserDTO(
            userId = 1L,
            name = "Jose",
            email = "jose@example.com",
            phoneNumber = "243343444",
            orders = mutableListOf(),
        ),
        notificationType = NotificationType.EMAIL
    )

    @BeforeAll
    fun setUp() {
        embeddedKafka.addTopics(topic)
        // embeddedKafka = EmbeddedKafkaBroker(1, false, topic)
        embeddedKafka.afterPropertiesSet()
        // embeddedKafka.start()

        val producerProps = KafkaTestUtils.producerProps(embeddedKafka)
        val producerFactory = DefaultKafkaProducerFactory<String, OrderDTO>(producerProps)
        producer = KafkaTemplate(producerFactory)
    }

    @AfterAll
    fun tearDown() {
        embeddedKafka.destroy()
    }

    // @Autowired
    // private lateinit var consumer: KafkaConsumer

    @Value("\${test.topic}")
    private lateinit var topic: String

    @Test
    fun `test Kafka producer and consumer using Embedded Kafka`() {
        val data = orderDtoExample
        producer.send(topic, data)

        val consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka)
        consumerProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        consumerProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        consumerProps[JsonDeserializer.TRUSTED_PACKAGES] = "*"

        val consumer = DefaultKafkaConsumerFactory<String, OrderDTO>(
            consumerProps,
            StringDeserializer(),
            JsonDeserializer(OrderDTO::class.java)
        ).createConsumer()

        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic)

        val records: ConsumerRecord<String, OrderDTO> = KafkaTestUtils.getSingleRecord(consumer, topic)

        val receivedMessage = records.value()

        assertNotNull(records.value())
        assertEquals(receivedMessage.orderId, orderDtoExample.orderId)
        assertEquals(receivedMessage.name, orderDtoExample.name)
        assertEquals(receivedMessage.price, orderDtoExample.price)
        assertEquals(receivedMessage.status, orderDtoExample.status)
        assertEquals(receivedMessage.userId, orderDtoExample.userId)
        // assertDoesNotThrow {  }

        consumer.close()
    }
}
