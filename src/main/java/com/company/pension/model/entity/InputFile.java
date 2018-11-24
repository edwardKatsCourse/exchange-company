package com.company.pension.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "input_file")
public class InputFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "filename", updatable = false, nullable = false)
    private String filename;

    @Lob
    @Column(name = "content", updatable = false, nullable = false)
    private byte [] content;

    @Column(name = "created_on")
    @CreatedDate
    private LocalDateTime createdOn;


}
