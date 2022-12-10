package edu.jong.board.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.jong.board.client.converter.LocalDateConverter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	OpenAPI openAPI(
			@Value("${springdoc.title}") String title,
			@Value("${springdoc.version}") String version) {
		return new OpenAPI().info(new Info().title(title).version(version));
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new LocalDateConverter());
		WebMvcConfigurer.super.addFormatters(registry);
	}
	
}
