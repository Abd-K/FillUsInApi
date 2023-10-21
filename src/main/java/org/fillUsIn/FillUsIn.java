package org.fillUsIn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

  @SpringBootApplication
  public class FillUsIn {

    public static void main(String[] args) {
      SpringApplication.run(FillUsIn.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
                  .allowedOrigins("*")
//                  .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5174")
                  .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true)
                  .maxAge(3600);
        }
      };
    }
  }