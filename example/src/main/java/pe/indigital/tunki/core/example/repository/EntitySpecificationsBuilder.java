package pe.indigital.tunki.core.example.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import pe.indigital.tunki.core.example.util.search.SearchCriteria;
import java.util.ArrayList;
import java.util.List;

public class EntitySpecificationsBuilder<T> {

    private List<SearchCriteria> params;

    EntitySpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public final EntitySpecificationsBuilder with(SearchCriteria searchCriteria) {
        if (!ObjectUtils.isEmpty(searchCriteria)) {
            params.add(searchCriteria);
        }
        return this;
    }

    public Specification<T> build() {
        if (ObjectUtils.isEmpty(this.params))
            return null;

        Specification<T> result = new EntitySpecification<>();

        for (SearchCriteria param : this.params) {
            result = Specification.where(result).and(new EntitySpecification<>(param));
        }

        return result;

    }

}
