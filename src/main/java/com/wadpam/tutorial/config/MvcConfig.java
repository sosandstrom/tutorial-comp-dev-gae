package com.wadpam.tutorial.config;

import com.wadpam.open.json.SkipNullObjectMapper;
import com.wadpam.open.user.config.UserConfig;
import com.wadpam.open.web.RestJsonExceptionResolver;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author os
 */
@EnableWebMvc
@Configuration
@Import(value={
    UserConfig.class
    })
public class MvcConfig extends WebMvcConfigurerAdapter {
    
    // -------------- Services -----------------------
    
    // -------------- Message Converters ----------------------

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        SkipNullObjectMapper skipNullMapper = new SkipNullObjectMapper();
        skipNullMapper.init();
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        converter.setObjectMapper(skipNullMapper);
        converters.add(converter);
    }
    
    // -------------- Serving Resources ----------------------

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("/static/")
                .addResourceLocations("classpath:/static/");
    }
    
    // -------------- Controllers ----------------------
    
    
    // -------------- View Stuff -----------------------

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(restJsonExceptionResolver());
    }
    
    public @Bean RestJsonExceptionResolver restJsonExceptionResolver() {
        final RestJsonExceptionResolver bean = new RestJsonExceptionResolver();
        bean.setOrder(100);
        return bean;
    }
    
}
