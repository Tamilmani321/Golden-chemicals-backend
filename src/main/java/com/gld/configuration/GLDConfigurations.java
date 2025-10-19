package com.gld.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class GLDConfigurations 
{
	@Value("${account.username}")
	private String username;
	@Bean
    public AuditorAware<String> auditorProvider() { // Registering audit log beans
        return () -> Optional.of(username);
    }
}
