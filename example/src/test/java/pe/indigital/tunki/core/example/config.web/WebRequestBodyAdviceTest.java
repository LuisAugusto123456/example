package pe.indigital.tunki.core.example.config.web;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import pe.indigital.tunki.core.example.util.logger.WebLogger;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class WebRequestBodyAdviceTest {

    @InjectMocks
    private WebRequestBodyAdvice webRequestBodyAdvice;

    @Mock
    private WebLogger webLogger;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private HttpInputMessage inputMessage;

    @Mock
    private Type targetType;

    @Test
    public void testSupports_ShouldReturnFalse() throws Exception {
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Boolean result = webRequestBodyAdvice.supports(methodParameter, targetType, aClass);
        assertFalse(result);
    }

    @Test
    public void testAfterBodyRead_ShouldReturnObject_WhenResponseIsObject() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object object = new Object();
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        doNothing().when(webLogger).logRequest(eq(httpServletRequest), eq(object));
        Object result = webRequestBodyAdvice.afterBodyRead(object, inputMessage, methodParameter, targetType, aClass);
        assertEquals(object, result);
    }

    @Test
    public void testAfterBodyRead_ShouldNotLog_WhenLogLevelIsNotDebug() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
        Object object = new Object();
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Object result = webRequestBodyAdvice.afterBodyRead(object, inputMessage, methodParameter, targetType, aClass);
        assertEquals(object, result);
    }
}
