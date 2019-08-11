package pe.indigital.tunki.core.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.ReadOnlyProperty;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import static pe.indigital.tunki.core.example.util.constants.RegExpConstants.*;

@Entity
@Table(name = "example")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class Example {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "example_id")
    @ReadOnlyProperty
    @Null(groups = {ExampleCreateValidation.class, ExampleUpdateValidation.class})
    @Pattern(regexp = UUID_V4_REGEX,
            groups = {ExampleCreateValidation.class, ExampleUpdateValidation.class},
            message = "invalid UUID"
    )
    private String exampleId;

    @Column(name = "description")
    @Length(min = 3, max = 255, groups = {ExampleCreateValidation.class, ExampleUpdateValidation.class})
    @NotEmpty(groups = {ExampleCreateValidation.class, ExampleUpdateValidation.class})
    private String description;

    private Boolean enabled;

    @Max(value = 10, groups = {ExampleCreateValidation.class, ExampleUpdateValidation.class})
    @Min(value = 1, groups = {ExampleCreateValidation.class, ExampleUpdateValidation.class})
    private Long status;

    @Column(name = "created_user")
    @NotEmpty(groups = {ExampleCreateValidation.class})
    @Null(groups = {ExampleUpdateValidation.class})
    private String createdUser;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_user")
    @NotEmpty(groups = {ExampleUpdateValidation.class})
    @Null(groups = {ExampleCreateValidation.class})
    private String lastModifiedUser;

    @Column(name = "lastModifiedDate", insertable = false)
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }

    public void copy(Example other) {
        if (other!=null) {

            if (other.getDescription()!=null) {
                this.description = other.getDescription();
            }

            if (other.getStatus()!=null) {
                this.status = other.getStatus();
            }

            if (other.getEnabled()!=null) {
                this.enabled = other.getEnabled();
            }

            if (other.getLastModifiedUser()!=null) {
            this.lastModifiedUser = other.getLastModifiedUser();
            }
        }
    }

    public interface ExampleCreateValidation {

    }

    public interface ExampleUpdateValidation {
    }
}
