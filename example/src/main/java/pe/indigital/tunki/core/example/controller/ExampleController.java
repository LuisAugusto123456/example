package pe.indigital.tunki.core.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.indigital.tunki.core.example.business.ExampleService;
import pe.indigital.tunki.core.example.config.web.ResponseEntityList;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.util.exception.EntityNotFoundException;
import java.util.Map;

@RestController
@RequestMapping(value = "/examples")
@Slf4j
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseEntityList> retrieveAllExamples(@RequestParam(required = false, defaultValue = "${application.pagination.page.number-default}") final Integer pageNumber,
                                                                   @RequestParam(required = false, defaultValue = "${application.pagination.page.size-default}") final Integer pageSize,
                                                                   @RequestParam(required = false) final Map<String, Object> queryMap) {
        ResponseEntityList responseEntityList = this.exampleService.listExample(queryMap, pageSize, pageNumber);
        HttpStatus status = HttpStatus.OK;
        if (responseEntityList.getMetadata().getCount() == 0) {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status).body(responseEntityList);
    }

    @GetMapping("/{exampleId}")
    @ResponseStatus(HttpStatus.OK)
    public Example retrieveExample(@PathVariable final String exampleId) throws EntityNotFoundException {
        return exampleService.getExample(exampleId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Example addExample(
            @Validated({Example.ExampleCreateValidation.class}) @RequestBody final Example request) {
        return this.exampleService.createExample(request);
    }

    @PutMapping("/{exampleId}")
    @ResponseStatus(HttpStatus.OK)
    public Example updateExample(@Validated({Example.ExampleUpdateValidation.class})
                                   @RequestBody final Example request,
                                   @PathVariable final String exampleId) throws EntityNotFoundException {
        return this.exampleService.updateExample(request, exampleId);
    }
}
