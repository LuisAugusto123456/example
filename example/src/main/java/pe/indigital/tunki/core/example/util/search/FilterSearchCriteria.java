package pe.indigital.tunki.core.example.util.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.indigital.tunki.core.example.util.constants.SearchConstants;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FilterSearchCriteria {

    DESCRIPTION(SearchConstants.SEARCH_DESCRIPTION, SearchOperation.LIKE);

    private String key;

    private SearchOperation operation;

    public static Stream<FilterSearchCriteria> stream() {
        return Stream.of(FilterSearchCriteria.values());
    }

}
