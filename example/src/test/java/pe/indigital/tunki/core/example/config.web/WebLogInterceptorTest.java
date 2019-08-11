package pe.indigital.tunki.core.example.config.web;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;
import pe.indigital.tunki.core.example.util.logger.WebLogger;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebLogInterceptorTest {

    @InjectMocks
    private WebLogInterceptor webLogInterceptor;

    @Mock
    private WebLogger webLogger;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private ModelAndView modelAndView;

    @Test
    public void testPreHandle_ShouldLog_WhenInterceptorIsForRequestAndGetMethod() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object handler = new Object();
        doNothing().when(webLogger).logRequest(eq(httpServletRequest), any());
        when(httpServletRequest.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(httpServletRequest.getMethod()).thenReturn(HttpMethod.GET.name());
        webLogInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        verify(webLogger, times(1)).logRequest(eq(httpServletRequest), any());
    }

    @Test
    public void testPreHandle_ShouldLog_WhenInterceptorIsNotForError() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object handler = new Object();
        when(httpServletRequest.getDispatcherType()).thenReturn(DispatcherType.ERROR);
        webLogInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        verify(webLogger, times(0)).logRequest(eq(httpServletRequest), any());
    }

    @Test
    public void testPreHandle_ShouldLog_WhenInterceptorIsForRequestAndPostMethod() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object handler = new Object();
        when(httpServletRequest.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST.name());
        webLogInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        verify(webLogger, times(0)).logRequest(eq(httpServletRequest), any());
    }

    @Test
    public void testPreHandle_ShouldNotLog_WhenLogLevelIsNotDebug() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
        Object handler = new Object();
        when(httpServletRequest.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(httpServletRequest.getMethod()).thenReturn(HttpMethod.GET.name());
        webLogInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        verify(webLogger, times(0)).logRequest(eq(httpServletRequest), any());
    }

}
