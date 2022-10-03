package com.edu.ulab.app_ylab.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.edu.ulab.app_ylab.entity")
@EnableJpaRepositories(basePackages = {"com.edu.ulab.app_ylab.repository"})
@ComponentScan({"com.edu.ulab.app_ylab.repository"})
public class SystemTestingJpaConfig {
}
