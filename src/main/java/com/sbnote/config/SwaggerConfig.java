package com.sbnote.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.net.HttpHeaders;

import io.swagger.models.auth.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {                          
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .ignoredParameterTypes(AuthenticationPrincipal.class)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.sbnote.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo())
          .securitySchemes(Arrays.asList(apiKey()));
        
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
     
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfo(
    			"Notes REST API",
    		    "API to save notes for every user.", 
    		    "1.0",
    		    "Terms of service",
    		    new Contact("Prakhar Rastogi", "www.knowshipp.com", "rastogi.prakhar@live.com"), 
    		    "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0");
    }
    
    private ApiKey apiKey() {
    	return new ApiKey("Authorization", HttpHeaders.AUTHORIZATION, In.HEADER.name());
    }
    
}
