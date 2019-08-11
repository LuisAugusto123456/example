package pe.indigital.tunki.core.example.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pe.indigital.tunki.core.example.config.web.ResponseEntityList;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.repository.ExampleRepository;
import pe.indigital.tunki.core.example.util.constants.SearchConstants;
import pe.indigital.tunki.core.example.util.exception.EntityNotFoundException;
import pe.indigital.tunki.core.example.util.exception.GenericErrors;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExampleServiceTest {

    @InjectMocks
    private ExampleService exampleService;

    @Mock
    private ExampleRepository exampleRepository;

    private Map<String, Object> parameters;

    private Example example;

    @Before
    public void setUp() {
        parameters = new HashMap<>();
        example = Example.builder()
            .exampleId(UUID.randomUUID().toString())
            .description("Jose Luis")
            .enabled(true)
            .status(1L)
            .createdDate(LocalDateTime.now())
            .createdUser(UUID.randomUUID().toString())
            .lastModifiedDate(LocalDateTime.now())
            .lastModifiedUser(UUID.randomUUID().toString())
            .build();
    }

    @Test
    public void testFindAll_ShouldReturnExampleList_WhenFoundSearchCriteria() throws Exception {
        parameters.put(SearchConstants.SEARCH_DESCRIPTION, "Jose");
        Integer pageSize = 2;
        Integer pageNumber = 0;
        List<Example> exampleList = new ArrayList<>();
        exampleList.add(example);
        Page<Example> page = new PageImpl(exampleList);
        when(exampleRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        ResponseEntityList<Example> result = exampleService.listExample(parameters, pageSize, pageNumber);
        assertNotNull(result);
        assertEquals(exampleList, result.getResult());
        assertEquals(pageNumber, result.getMetadata().getPageNumber());
        assertEquals(pageSize, result.getMetadata().getPageSize());
        verify(exampleRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void testFindAll_ShouldReturnEmptyList_WhenNotFoundSearchCriteria() throws Exception {
        parameters.put(SearchConstants.SEARCH_DESCRIPTION, "Jose");
        Integer pageSize = 2;
        Integer pageNumber = 0;
        List<Example> exampleList = new ArrayList<>();
        Page<Example> page = new PageImpl(exampleList);
        when(exampleRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        ResponseEntityList<Example> result = exampleService.listExample(parameters, pageSize, pageNumber);
        assertNotNull(result);
        assertEquals(exampleList, result.getResult());
        assertEquals(pageNumber, result.getMetadata().getPageNumber());
        assertEquals(pageSize, result.getMetadata().getPageSize());
        assertEquals(0L, result.getMetadata().getCount(), 0L);
        verify(exampleRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void testRetrieveExample_ShouldReturnExample_WhenFoundInRepository() throws Exception {
        String exampleId = UUID.randomUUID().toString();
        when(exampleRepository.findById(eq(exampleId))).thenReturn(Optional.of(example));
        Example result = exampleService.getExample(exampleId);
        verify(exampleRepository, times(1)).findById(eq(exampleId));
        assertNotNull(result);
        assertEquals(example, result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testRetrieveExample_ShouldReturnException_WhenWasNotFoundInRepository() throws Exception {
        String exampleId = UUID.randomUUID().toString();
        when(exampleRepository.findById(eq(exampleId))).thenReturn(Optional.empty());
        try {
            exampleService.getExample(exampleId);
        } catch (EntityNotFoundException ex) {
            assertEquals(GenericErrors.GE_02.name(), ex.getCode());
            assertEquals(String.format(GenericErrors.GE_02.getMessage(), exampleId), ex.getMessage());
            assertEquals(Example.class.getSimpleName().toLowerCase(), ex.getEntity());
            throw ex;
        }
    }

    @Test
    public void testUpdateExample_ShouldReturnExample_WhenWasUpdatedInRepository() throws Exception {
        String exampleId = UUID.randomUUID().toString();
        when(exampleRepository.findById(eq(exampleId))).thenReturn(Optional.of(example));
        when(exampleRepository.saveAndFlush(eq(example))).thenReturn(example);
        Example result = exampleService.updateExample(example, exampleId);
        assertNotNull(result);
        assertEquals(example, result);
        verify(exampleRepository, times(1)).saveAndFlush(eq(example));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateExample_ShouldReturnException_WhenWasNotUpdatedInRepository() throws Exception {
        String exampleId = UUID.randomUUID().toString();
        when(exampleRepository.findById(eq(exampleId))).thenReturn(Optional.empty());
        try {
            exampleService.updateExample(example, exampleId);
        } catch (EntityNotFoundException ex) {
            assertEquals(GenericErrors.GE_02.name(), ex.getCode());
            assertEquals(String.format(GenericErrors.GE_02.getMessage(), exampleId), ex.getMessage());
            assertEquals(Example.class.getSimpleName().toLowerCase(), ex.getEntity());
            throw ex;
        }
    }

    @Test
    public void testCreateExample_ShouldReturnExample_WhenPersistInRepository() {
        Example example = Example.builder().build();
        when(exampleRepository.saveAndFlush(eq(example))).thenReturn(example);
        Example result = exampleService.createExample(example);
        assertNotNull(result);
        assertEquals(example, result);
        verify(exampleRepository, times(1)).saveAndFlush(eq(example));
    }
}
