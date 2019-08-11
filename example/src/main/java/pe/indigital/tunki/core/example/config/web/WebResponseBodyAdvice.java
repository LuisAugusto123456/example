package pe.indigital.tunki.core.example.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pe.indigital.tunki.core.example.util.exception.EntityValidationWrapper;
import pe.indigital.tunki.core.example.util.logger.WebLogger;

@Slf4j
public class WebResponseBodyAdvice extends EntityValidationWrapper implements ResponseBodyAdvice<Object> {

    private WebLogger webLogger;

    public WebResponseBodyAdvice(WebLogger webLogger) {
        this.webLogger = webLogger;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            if (log.isDebugEnabled()) {
                webLogger.logResponse(
                        ((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                        ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), body);
            }
        }

        return body;
    }
}
