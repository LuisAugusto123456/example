package pe.indigital.tunki.core.example.util.search;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;

}
