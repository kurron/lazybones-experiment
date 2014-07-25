package org.example

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableConfigurationProperties( ApplicationProperties )
class WebContext extends WebMvcConfigurerAdapter {

    @Bean
    EmbeddedServletContainerEventListener embeddedServletContainerEventListener() {
        new EmbeddedServletContainerEventListener()
    }
}
