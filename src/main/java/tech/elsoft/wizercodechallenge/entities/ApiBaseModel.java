package tech.elsoft.wizercodechallenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@SoftDelete
@AllArgsConstructor
@NoArgsConstructor
public class ApiBaseModel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime dateModified;


}
