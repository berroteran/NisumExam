package com.nisum.nisumexam.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.io.IOException;
import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@EnableAutoConfiguration(exclude = SpringDocDataRestConfiguration.class)
@ComponentScan(basePackages = {"com.nisum.nisumexam.controller"}, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {SwaggerConfig.SpringDocDataRestFilter.class, SwaggerConfig.SpringDocHateoasFilter.class})})
public class SwaggerConfig {
  
  @Bean
  public OpenAPI openAPI() {
    
    return new OpenAPI()
        .info(
            new Info()
            .title("NISUM TEST webapi")
            .description("Order Service API Description")
            .contact(
              new Contact()
              .email("omarberroteranlkf@gmail.com")
              .name("Omar Berroteran")
              .url("https://www.linkedin.com/in/omarberroteransilva/")
            )
            .version("1.0").license(new License())
        );
    
  }
  
  public static class SpringDocDataRestFilter extends ClassFilter {
    
    @Override
    protected Class<?> getFilteredClass() {
      return SpringDocDataRestConfiguration.class;
    }
  }
  
  public static class SpringDocHateoasFilter extends ClassFilter {
    
    @Override
    protected Class<?> getFilteredClass() {
      return SpringDocHateoasConfiguration.class;
    }
  }
  
  private static abstract class ClassFilter implements TypeFilter {
    
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
      String className          = metadataReader.getClassMetadata().getClassName();
      String enclosingClassName = metadataReader.getClassMetadata().getEnclosingClassName();
      return className.equals(getFilteredClass().getCanonicalName()) || (enclosingClassName != null && enclosingClassName.equals(getFilteredClass().getCanonicalName()));
    }
    
    protected abstract Class<?> getFilteredClass();
  }
}