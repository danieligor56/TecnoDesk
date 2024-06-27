package br.com.tecnoDesk.TecnoDesk.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import Tenant.TenantInterceptor;

/*
 * @Configuration public class WebConfig implements WebMvcConfigurer{
 * 
 * public void addInterceptors(InterceptorRegistry registry) {
 * registry.addInterceptor(new TenantInterceptor()); }
 * 
 * }
 */