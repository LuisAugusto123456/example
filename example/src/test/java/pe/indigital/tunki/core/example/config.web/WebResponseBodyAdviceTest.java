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
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import pe.indigital.tunki.core.example.util.logger.WebLogger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebResponseBodyAdviceTest {

    @InjectMocks
    private WebResponseBodyAdvice webResponseBodyAdvice;

    @Mock
    private WebLogger webLogger;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private MediaType mediaType;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Test
    public void testSupports_ShouldReturnFalse() throws Exception {
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Boolean result = webResponseBodyAdvice.supports(methodParameter, aClass);
        assertFalse(result);
    }

    @Test
    public void testBeforeBodyWrite_ShouldReturnObject_WhenResponseIsObject() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object object = new Object();
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Object result = webResponseBodyAdvice.beforeBodyWrite(object, methodParameter, mediaType, aClass, serverHttpRequest, serverHttpResponse);
        assertEquals(object, result);
    }

    @Test
    public void testBeforeBodyWrite_ShouldReturnObject_WhenRequesIsServletServer() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object object = new Object();
        ServletServerHttpRequest serverHttpRequest = mock(ServletServerHttpRequest .class);
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Object result = webResponseBodyAdvice.beforeBodyWrite(object, methodParameter, mediaType, aClass, serverHttpRequest, serverHttpResponse);
        assertEquals(object, result);
    }

    @Test
    public void testBeforeBodyWrite_ShouldReturnObject_WhenResponseIsServletServer() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object object = new Object();
        ServletServerHttpResponse  serverHttpResponse = mock(ServletServerHttpResponse.class);
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Object result = webResponseBodyAdvice.beforeBodyWrite(object, methodParameter, mediaType, aClass, serverHttpRequest, serverHttpResponse);
        assertEquals(object, result);
    }

    @Test
    public void testBeforeBodyWrite_ShouldReturnObject_WhenRequestAndResponseAreServletServer() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        Object object = new Object();
        ServletServerHttpRequest  serverHttpRequest = mock(ServletServerHttpRequest .class);
        ServletServerHttpResponse serverHttpResponse = mock(ServletServerHttpResponse.class);
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Object result = webResponseBodyAdvice.beforeBodyWrite(object, methodParameter, mediaType, aClass, serverHttpRequest, serverHttpResponse);
        assertEquals(object, result);
    }

    @Test
    public void testBeforeBodyWrite_ShouldNotLog_WhenLogLevelIsNotDebug() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
        Object object = new Object();
        ServletServerHttpRequest  serverHttpRequest = mock(ServletServerHttpRequest .class);
        ServletServerHttpResponse serverHttpResponse = mock(ServletServerHttpResponse.class);
        Class aClass = Class.forName("pe.indigital.tunki.core.MainApplication");
        Object result = webResponseBodyAdvice.beforeBodyWrite(object, methodParameter, mediaType, aClass, serverHttpRequest, serverHttpResponse);
        assertEquals(object, result);
    }


}
