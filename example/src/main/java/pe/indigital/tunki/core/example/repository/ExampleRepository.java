package pe.indigital.tunki.core.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pe.indigital.tunki.core.example.entity.Example;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface ExampleRepository extends JpaRepository<Example, String>, JpaSpecificationExecutor<Example> {

}
