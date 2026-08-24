package com.riyaz.ecom.jpademo.models;

import com.riyaz.ecom.jpademo.models.Teacher;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "student")
    private Set<TeacherRating> ratings;
}
