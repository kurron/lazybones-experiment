package org.kurron.example.inbound.amqp

import org.springframework.amqp.rabbit.core.RabbitManagementTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Beans only required for testing.
 */
@Configuration
class TestConfiguration {

    @Bean
    RabbitManagementTemplate rabbitManagementTemplate() {
        new RabbitManagementTemplate()
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        new Jackson2JsonMessageConverter()
    }
}
