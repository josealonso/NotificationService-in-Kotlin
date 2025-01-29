package com.josealonso.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy
class AspectConfig {
    // This class can be left empty.
    // The annotations above are sufficient to enable aspect-oriented programming.
    // Spring Boot provides auto-configuration for Kafka
}
