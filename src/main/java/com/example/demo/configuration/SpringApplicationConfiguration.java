package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "com.example")
@EnableWebMvc
public class SpringApplicationConfiguration implements WebMvcConfigurer{

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver ivr=new InternalResourceViewResolver();
        ivr.setPrefix("/views/");
        ivr.setSuffix(".jsp");
        ivr.setExposeContextBeansAsAttributes(true);
        registry.viewResolver(ivr);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");  // path web/resources/
    }

}
