package pe.indigital.tunki.core.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import pe.indigital.tunki.core.example.business.ExampleService;
import pe.indigital.tunki.core.example.config.web.MetadataProperties;
import pe.indigital.tunki.core.example.config.web.ResponseEntityList;
import pe.indigital.tunki.core.example.entity.Example;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExampleControllerTest {

    @InjectMocks
    private ExampleController exampleController;

    @Mock
    private ExampleService exampleService;

    @Test
    public void testRetrieveAllExamples_ShouldReturnExample_WhenSearchCriteriaMatch() {
        Integer pageSize = 2;
        Integer pageNumber = 0;
        Long elements = 1L;
        Map<String, Object> parameters = new HashMap<>();
        ResponseEntityList<Example> response = new ResponseEntityList<>();
        response.setMetadata(MetadataProperties.builder().pageSize(pageSize).pageNumber(pageNumber).count(elements).build());
        when(exampleService.listExample(eq(parameters), eq(pageSize), eq(pageNumber))).thenReturn(response);
        ResponseEntity<ResponseEntityList> result = exampleController.retrieveAllExamples(pageNumber, pageSize, parameters);
        verify(exampleService, times(1)).listExample(eq(parameters), eq(pageSize), eq(pageNumber));
        assertEquals(response, result.getBody());
    }

    @Test
    public void testRetrieveAllExamples_ShouldReturnEmptyList_WhenSearchCriteriaNotMatch() {
        Integer pageSize = 2;
        Integer pageNumber = 0;
        Long elements = 0L;
        Map<String, Object> parameters = new HashMap<>();
        ResponseEntityList<Example> response = new ResponseEntityList<>();
        response.setMetadata(MetadataProperties.builder().pageSize(pageSize).pageNumber(pageNumber).count(elements).build());
        when(exampleService.listExample(eq(parameters), eq(pageSize), eq(pageNumber))).thenReturn(response);
        ResponseEntity<ResponseEntityList> result = exampleController.retrieveAllExamples(pageNumber, pageSize, parameters);
        verify(exampleService, times(1)).listExample(eq(parameters), eq(pageSize), eq(pageNumber));
        assertEquals(response, result.getBody());
    }

    @Test
    public void testRetrieveExample_ShouldReturnExample_WhenWasFound() throws Exception {
        String exampleId = UUID.randomUUID().toString();
        Example example = Example.builder().build();
        when(exampleService.getExample(eq(exampleId))).thenReturn(example);
        Example result = exampleController.retrieveExample(exampleId);
        verify(exampleService, times(1)).getExample(eq(exampleId));
        assertEquals(example, result);
    }

    @Test
    public void testCreateExample_ShouldReturnExample_WhenWasCreated() {
        Example example = Example.builder().build();
        when(exampleService.createExample(eq(example))).thenReturn(example);
        Example result = exampleController.addExample(example);
        verify(exampleService, times(1)).createExample(eq(example));
        assertEquals(example, result);
    }

    @Test
    public void updateExample() throws Exception {
        String exampleId = UUID.randomUUID().toString();
        Example example = Example.builder().build();
        when(exampleService.updateExample(eq(example), eq(exampleId))).thenReturn(example);
        Example result = exampleController.updateExample(example, exampleId);
        verify(exampleService, times(1)).updateExample(eq(example), eq(exampleId));
        assertEquals(example, result);
    }
}
