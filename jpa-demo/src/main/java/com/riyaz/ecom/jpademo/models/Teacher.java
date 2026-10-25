package com.riyaz.ecom.jpademo.models;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Teacher {

    @Id
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "teachers") // Maps cleanly to the field in Student
    private Set<Student> students;

    @OneToMany(mappedBy = "teacher")
    private Set<TeacherRating> ratings;
}