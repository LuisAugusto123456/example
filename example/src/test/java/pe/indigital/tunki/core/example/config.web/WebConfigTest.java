package pe.indigital.tunki.core.example.config.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import pe.indigital.tunki.core.example.util.logger.WebLogger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebConfigTest {

    @InjectMocks
    private WebConfig webConfig;

    @Mock
    private WebLogger webLogger;

    @Mock
    private InterceptorRegistry interceptorRegistry;

    @Test
    public void testAddInterceptors_ShouldAddInterceptor() {
        InterceptorRegistration registration = mock(InterceptorRegistration.class);
        when(interceptorRegistry.addInterceptor(any(HandlerInterceptor.class))).thenReturn(registration);
        webConfig.addInterceptors(interceptorRegistry);
        verify(interceptorRegistry, times(1)).addInterceptor(any(HandlerInterceptor.class));
    }
}
