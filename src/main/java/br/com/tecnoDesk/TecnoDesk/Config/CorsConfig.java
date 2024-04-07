package br.com.tecnoDesk.TecnoDesk.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableWebMvc
public class CorsConfig {
		
		 @Bean
		    public CorsFilter corsFilter() {
		        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		        CorsConfiguration config = new CorsConfiguration();
		        config.addAllowedOrigin("*");
		        config.addAllowedHeader("*");
		        config.addAllowedMethod("*");
		        source.registerCorsConfiguration("/**", config);
		        return new CorsFilter(source);
		    }
		}


