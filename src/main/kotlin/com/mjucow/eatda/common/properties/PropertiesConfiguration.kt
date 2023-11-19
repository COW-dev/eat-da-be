package com.mjucow.eatda.common.properties

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["com.mjucow.eatda.common.properties"])
class PropertiesConfiguration
