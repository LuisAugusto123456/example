package pe.indigital.tunki.core.example.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pe.indigital.tunki.core.example.util.logger.WebLogger;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class WebLogInterceptor implements HandlerInterceptor {

    private WebLogger webLogger;

    public WebLogInterceptor(WebLogger webLogger) {
        this.webLogger = webLogger;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name()) && log.isDebugEnabled() ) {
            webLogger.logRequest(request, null);
        }
        return true;
    }

}
