package team1.spring.training.controllers.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean loggingFilter(){
        FilterRegistrationBean registrationBean
                = new FilterRegistrationBean();

        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
