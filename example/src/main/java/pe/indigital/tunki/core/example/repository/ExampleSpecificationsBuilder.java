package pe.indigital.tunki.core.example.repository;

import org.springframework.util.ObjectUtils;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.util.search.FilterSearchCriteria;
import pe.indigital.tunki.core.example.util.search.SearchCriteria;
import java.util.Map;

public class ExampleSpecificationsBuilder extends EntitySpecificationsBuilder<Example> {

    public ExampleSpecificationsBuilder(Map<String, Object> queryMap) {
        if (!ObjectUtils.isEmpty(queryMap)) {
            FilterSearchCriteria.stream().forEach(criteria -> {
                Object value = queryMap.get(criteria.getKey());
                if(!ObjectUtils.isEmpty(value)) {
                    this.with(SearchCriteria.builder()
                        .key(criteria.getKey())
                        .operation(criteria.getOperation())
                        .value(queryMap.get(criteria.getKey()))
                        .build());
                }
            });
        }
    }

}
