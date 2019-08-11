package pe.indigital.tunki.core.example.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.util.search.SearchCriteria;
import pe.indigital.tunki.core.example.util.search.SearchOperation;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ExampleSpecificationsBuilderTest {

    private ExampleSpecificationsBuilder exampleSpecificationsBuilder;

    @Before
    public void setUp() {
        Map<String, Object> queryMap = new HashMap<>();
        exampleSpecificationsBuilder = new ExampleSpecificationsBuilder(queryMap);
    }

    @Test
    public void testWith_ShouldAddedAsParameter_WhenSearchCriteriaIsAdded() {
        SearchCriteria parameter = SearchCriteria.builder()
            .key("name")
            .operation(SearchOperation.EQUALITY)
            .value("Jose Luis")
            .build();
        exampleSpecificationsBuilder.with(parameter);
        Specification<Example> result = exampleSpecificationsBuilder.build();
        assertNotNull(result);
    }

    @Test
    public void testWith_ShouldAddedAsParameter_WhenSearchCriteriaIsNull() {
        SearchCriteria parameter = null;
        exampleSpecificationsBuilder.with(parameter);
        Specification<Example> result = exampleSpecificationsBuilder.build();
        assertNull(result);
    }

    @Test
    public void testBuild_ShouldReturnSpecification_WhenParametersListIsNotNull() {
        exampleSpecificationsBuilder.with(SearchCriteria.builder()
            .key("email").operation(SearchOperation.LIKE).value("joseperez@indigital.com").build());
        exampleSpecificationsBuilder.with(SearchCriteria.builder()
            .key("name").operation(SearchOperation.EQUALITY).value("Jose Luis").build());
        Specification<Example> result = exampleSpecificationsBuilder.build();
        assertNotNull(result);
    }

    @Test
    public void testBuild_ShouldReturnNull_WhenParametersListIsNull() {
        Specification<Example> result = exampleSpecificationsBuilder.build();
        assertNull(result);
    }
}
