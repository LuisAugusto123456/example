package pe.indigital.tunki.core.example.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pe.indigital.tunki.core.example.util.logger.WebLogger;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private WebLogger webLogger;

    @Autowired
    public WebConfig(WebLogger webLogger) {
        this.webLogger = webLogger;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebLogInterceptor(webLogger)).addPathPatterns("/**");
    }


}
