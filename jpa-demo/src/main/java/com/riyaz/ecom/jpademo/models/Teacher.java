package com.riyaz.ecom.jpademo.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "teachers")
    private Set<Student> students;

    @OneToMany(mappedBy = "teacher")
    private Set<TeacherRating> ratings;
}
