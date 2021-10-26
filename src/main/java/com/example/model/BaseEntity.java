package com.example.model;

import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD

import javax.persistence.*;
=======
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
>>>>>>> 1fd69002eef85d5633ac06464fcb5ba9aa763714

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

<<<<<<< HEAD
=======
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Instant modifiedAt;

>>>>>>> 1fd69002eef85d5633ac06464fcb5ba9aa763714
}
