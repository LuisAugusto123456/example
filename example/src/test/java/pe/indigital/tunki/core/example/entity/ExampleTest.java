package pe.indigital.tunki.core.example.entity;

import org.junit.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.Assert.*;

public class ExampleTest {

    @Test
    public void testCopy_ShouldReturnExample_WhenNewExampleHaveData() {
        Example newExample = Example.builder()
            .exampleId(UUID.randomUUID().toString())
            .description("Entidad nueva")
            .enabled(true)
            .status(1L)
            .createdDate(LocalDateTime.now())
            .createdUser(UUID.randomUUID().toString())
            .lastModifiedDate(LocalDateTime.now())
            .lastModifiedUser(UUID.randomUUID().toString())
            .build();
        Example example = Example.builder().build();
        example.copy(newExample);
        assertEquals(newExample.getDescription(), example.getDescription());
        assertEquals(newExample.getEnabled(), example.getEnabled());
        assertEquals(newExample.getStatus(), example.getStatus());
        assertEquals(newExample.getLastModifiedUser(), example.getLastModifiedUser());
    }

    @Test
    public void testCopy_ShouldReturnEmptyExample_WhenNewExampleIsNull() {
        Example newExample = null;
        Example example = Example.builder().build();
        example.copy(newExample);
        assertNotNull(example);
        assertNull(example.getDescription());
    }

    @Test
    public void testCopy_ShouldReturnEmptyExample_WhenNewExampleIsEmpty() {
        Example newExample = Example.builder().build();
        Example example = Example.builder().build();
        example.copy(newExample);
        assertNotNull(example);
        assertNull(example.getDescription());
    }

    @Test
    public void testPersist_ShouldReturnCreatedDate_WhenNewExampleIsCreated() {
        Example newExample = Example.builder().build();
        assertNull(newExample.getCreatedDate());
        newExample.prePersist();
        assertNotNull(newExample.getCreatedDate());
    }

    @Test
    public void testUpdate_ShouldReturnCreatedDate_WhenNewExampleIsCreated() {
        Example newExample = Example.builder().build();
        assertNull(newExample.getLastModifiedDate());
        newExample.preUpdate();
        assertNotNull(newExample.getLastModifiedDate());
    }
}
