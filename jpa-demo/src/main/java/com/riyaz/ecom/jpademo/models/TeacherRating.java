package com.riyaz.ecom.jpademo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_rating")
public class TeacherRating {

    @EmbeddedId
    private TeacherRatingKey id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private int rating;
}
