package com.riyaz.ecom.jpademo.models;

import com.riyaz.ecom.jpademo.models.Teacher;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Student {

    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "students_teachers", // Matches test Table Name
            joinColumns = @JoinColumn(name = "student_id"), // Matches test Column
            inverseJoinColumns = @JoinColumn(name = "teacher_id") // Matches test Column
    )
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "student")
    private Set<TeacherRating> ratings;
}