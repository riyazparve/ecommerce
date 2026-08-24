package com.riyaz.ecom.jpademo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class TeacherRatingKey implements Serializable {
    private Long studentId;
    private Long teacherId;
}
