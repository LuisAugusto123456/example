package pe.indigital.tunki.core.example.util.logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebLoggerTest {

    private WebLogger webLogger;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Before
    public void setUp() {
        webLogger = new WebLogger();
    }

    @Test
    public void testLogRequest_ShouldLogWebRequest_WhenRequestHasParametersAndBody() {
        Object object = new Object();
        String key = "name";
        String value = "Jose Luis";
        Enumeration<String> parameters = mock(Enumeration.class);
        Enumeration<String> headers = mock(Enumeration.class);
        when(parameters.hasMoreElements()).thenReturn(true).thenReturn(false);
        when(parameters.nextElement()).thenReturn(key);
        when(httpServletRequest.getParameter(eq(key))).thenReturn(value);
        when(httpServletRequest.getParameterNames()).thenReturn(parameters);
        when(headers.hasMoreElements()).thenReturn(true).thenReturn(false);
        when(headers.nextElement()).thenReturn(key);
        when(httpServletRequest.getHeaderNames()).thenReturn(headers);
        webLogger.logRequest(httpServletRequest, object);
        verify(httpServletRequest, times(1)).getParameterNames();
        verify(httpServletRequest, times(1)).getHeaderNames();
    }

    @Test
    public void testLogRequest_ShouldLogWebRequest_WhenRequestHasNotParametersAndBody() {
        Enumeration<String> parameters = mock(Enumeration.class);
        Enumeration<String> headers = mock(Enumeration.class);
        when(parameters.hasMoreElements()).thenReturn(false);
        when(httpServletRequest.getParameterNames()).thenReturn(parameters);
        when(headers.hasMoreElements()).thenReturn(false);
        when(httpServletRequest.getHeaderNames()).thenReturn(headers);
        webLogger.logRequest(httpServletRequest, null);
        verify(httpServletRequest, times(1)).getParameterNames();
        verify(httpServletRequest, times(1)).getHeaderNames();
    }

    @Test
    public void testLogResponse_ShouldLogWebResponse_WhenResponseHasHeaders() {
        String header = "device";
        Object object = new Object();
        when(httpServletRequest.getMethod()).thenReturn("GET");
        when(httpServletRequest.getRequestURI()).thenReturn("/examples");
        when(httpServletResponse.getStatus()).thenReturn(200);
        Collection<String> headers = new ArrayList<>();
        headers.add(header);
        when(httpServletResponse.getHeader(eq(header))).thenReturn("Android");
        when(httpServletResponse.getHeaderNames()).thenReturn(headers);
        webLogger.logResponse(httpServletRequest, httpServletResponse, object);
        verify(httpServletResponse, times(1)).getHeaderNames();
    }

    @Test
    public void testLogResponse_ShouldLogWebResponse_WhenResponseHasNotHeaders() {
        Object object = new Object();
        when(httpServletRequest.getMethod()).thenReturn("GET");
        when(httpServletRequest.getRequestURI()).thenReturn("/examples");
        when(httpServletResponse.getStatus()).thenReturn(200);
        Collection<String> headers = new ArrayList<>();
        when(httpServletResponse.getHeaderNames()).thenReturn(headers);
        webLogger.logResponse(httpServletRequest, httpServletResponse, object);
        verify(httpServletResponse, times(1)).getHeaderNames();
    }
}
