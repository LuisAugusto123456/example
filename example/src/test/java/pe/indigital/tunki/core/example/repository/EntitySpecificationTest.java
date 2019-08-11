package pe.indigital.tunki.core.example.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.util.search.SearchCriteria;
import pe.indigital.tunki.core.example.util.search.SearchOperation;
import javax.persistence.criteria.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static pe.indigital.tunki.core.example.util.search.SearchOperation.OTHER;

@RunWith(MockitoJUnitRunner.class)
public class EntitySpecificationTest {

    private EntitySpecification entitySpecification;

    @Mock
    private Root<Example> root;

    @Mock
    private CriteriaQuery<?> criteriaQuery;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Test
    public void toPredicate_ShouldReturnPredicate_WhenSearchCriteriaIsEquality() {
        Path path = mock(Path.class);
        Predicate predicate = mock(Predicate.class);
        SearchCriteria parameter = SearchCriteria.builder()
            .key("email")
            .operation(SearchOperation.EQUALITY)
            .value("joseperez@indigital.com")
            .build();
        entitySpecification = new EntitySpecification(parameter);
        when(root.get(eq(parameter.getKey()))).thenReturn(path).thenReturn(path);
        when(criteriaBuilder.equal(eq(path), eq(parameter.getValue()))).thenReturn(predicate);
        Predicate result = entitySpecification.toPredicate(root, criteriaQuery, criteriaBuilder);
        assertNotNull(result);
        assertEquals(predicate, result);
        verify(criteriaBuilder, times(1)).equal(eq(path), eq(parameter.getValue()));
    }

    @Test
    public void toPredicate_ShouldReturnPredicate_WhenSearchCriteriaIsLike() {
        Path path = mock(Path.class);
        Predicate predicate = mock(Predicate.class);
        SearchCriteria parameter = SearchCriteria.builder()
            .key("name")
            .operation(SearchOperation.LIKE)
            .value("Jose Luis")
            .build();
        entitySpecification = new EntitySpecification(parameter);
        when(root.get(eq(parameter.getKey()))).thenReturn(path);
        when(criteriaBuilder.like(eq(path), eq("%" + parameter.getValue().toString() + "%"))).thenReturn(predicate);
        Predicate result = entitySpecification.toPredicate(root, criteriaQuery, criteriaBuilder);
        assertNotNull(result);
        assertEquals(predicate, result);
        verify(criteriaBuilder, times(1)).like(eq(path), eq("%" + parameter.getValue().toString() + "%"));
    }

    @Test
    public void toPredicate_ShouldReturnNull_WhenSearchCriteriaIsNotValid() {
        SearchCriteria parameter = SearchCriteria.builder().operation(OTHER).build();
        entitySpecification = new EntitySpecification(parameter);
        Predicate result = entitySpecification.toPredicate(root, criteriaQuery, criteriaBuilder);
        assertNull(result);
    }

}
