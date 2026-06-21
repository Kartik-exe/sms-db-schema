package com.schema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "MARKS")
    private Integer marks;

    @Column(name = "GRADE")
    private String grade;

    @ManyToOne
    private Student student;

    @Override
    public int hashCode() {
        return Objects.hash(sid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Marks other = (Marks) obj;
        return Objects.equals(sid, other.sid);
    }
}

