package com.riyaz.ecom.jpademo.models;


import jakarta.persistence.*;

@Entity
public class TeacherRating {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private int rating;
}