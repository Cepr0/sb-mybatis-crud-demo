package io.github.cepr0.demo;

import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	@Bean
	ConfigurationCustomizer mybatisConfigurationCustomizer() {
		return configuration -> {
			TypeHandlerRegistry handlerRegistry = configuration.getTypeHandlerRegistry();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
