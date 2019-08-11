package pe.indigital.tunki.core.example.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pe.indigital.tunki.core.example.config.web.MetadataProperties;
import pe.indigital.tunki.core.example.config.web.ResponseEntityList;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.repository.ExampleRepository;
import pe.indigital.tunki.core.example.repository.ExampleSpecificationsBuilder;
import pe.indigital.tunki.core.example.util.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;

    public ResponseEntityList<Example> listExample(final Map<String, Object> parameters, final Integer pageSize, final Integer pageNumber) {
        List<Example> examples = new ArrayList<>();
        long totalElements = 0L;
        ExampleSpecificationsBuilder searchBuilder = new ExampleSpecificationsBuilder(parameters);
        MetadataProperties metadata;
        Pageable page = PageRequest.of(Integer.parseInt(pageNumber.toString()),
                Integer.parseInt(pageSize.toString()));
        Page<Example> result = this.exampleRepository.findAll(searchBuilder.build(), page);
        if(!ObjectUtils.isEmpty(result.getContent())) {
            totalElements = result.getTotalElements();
            examples = result.getContent();
        }
        metadata = MetadataProperties.builder()
                .pageSize(Integer.parseInt(pageSize.toString()))
                .pageNumber(Integer.parseInt(pageNumber.toString()))
                .count(totalElements)
                .build();
        return ResponseEntityList.<Example>builder().result(examples).metadata(metadata).build();
    }

    public Example getExample(final String exampleId) throws EntityNotFoundException {
        return this.exampleRepository.findById(exampleId).orElseThrow(() -> new EntityNotFoundException(Example.class, exampleId));
    }

    public Example updateExample(final Example exampleUpdated, final String exampleId) throws EntityNotFoundException {
        Example example = this.getExample(exampleId);
        example.copy(exampleUpdated);
        return this.exampleRepository.saveAndFlush(example);
    }

    public Example createExample(final Example exampleNew) {
        return this.exampleRepository.saveAndFlush(exampleNew);
    }

}