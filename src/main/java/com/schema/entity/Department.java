package com.schema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @Column(name = "HOD_NAME")
    private String hodName;

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
        Department other = (Department) obj;
        return Objects.equals(sid, other.sid);
    }

}
