package com.wadpam.tutorial.config;

import com.wadpam.oauth2.service.OAuth2OpenUserService;
import com.wadpam.oauth2.web.OAuth2Interceptor;
import com.wadpam.open.config.DomainConfig;
import com.wadpam.open.json.SkipNullObjectMapper;
import com.wadpam.open.user.config.UserConfig;
import com.wadpam.open.web.BasicAuthenticationInterceptor;
import com.wadpam.open.web.RestJsonExceptionResolver;
import com.wadpam.tutorial.service.TutorialService;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author os
 */
@EnableWebMvc
@Configuration
@Import(value={
    DomainConfig.class,
    UserConfig.class
    })
@ImportResource(value={"classpath:/oauth2-client-context.xml","classpath:/interceptor-security.xml"})
public class MvcConfig extends WebMvcConfigurerAdapter {
    
    // -------------- Services -----------------------
    
    @Bean
    public OAuth2OpenUserService oauth2UserService() {
        // the openUserService will be auto-wired
        return new OAuth2OpenUserService();
    }
    
    @Bean(initMethod = "init")
    public TutorialService tutorialService() {
        // domainService and factoryService will be auto-wired
        return new TutorialService();
    }
    
    // -------------- Message Converters ----------------------

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        SkipNullObjectMapper skipNullMapper = new SkipNullObjectMapper();
        skipNullMapper.init();
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        converter.setObjectMapper(skipNullMapper);
        converters.add(converter);
    }
    
    // -------------- Interceptors -----------------------
    
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(basicAuthenticationInterceptor());
//        registry.addInterceptor(oauth2Interceptor());
//    }
//    
//    @Bean
//    public BasicAuthenticationInterceptor basicAuthenticationInterceptor() {
//        BasicAuthenticationInterceptor bean = new BasicAuthenticationInterceptor();
//        return bean;
//    }
//    
//    @Bean
//    public OAuth2Interceptor oauth2Interceptor() {
//        OAuth2Interceptor bean = new OAuth2Interceptor();
//        
//        // prepare the whitelist for OAuth2Interceptor
//        Map.Entry<String, Collection<String>> registerFederated = 
//                
//                // /api/tutorial/federated/v11 for GET and POST:
//                new AbstractMap.SimpleImmutableEntry(
//                    "\\A/api/[^/]+/federated/v.*", 
//                    Arrays.asList("GET", "POST"));
//        
//        bean.setWhitelistedMethods(Arrays.asList(registerFederated));
//        
//        return bean;
//    }
    
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
