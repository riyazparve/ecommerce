package com.riyaz.ecom.jpademo.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "INSTRUCTOR")
public class Instructor {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @ManyToMany
    @JoinTable(name = "INSTRUCTORS_BATCHES", 
    joinColumns = @JoinColumn(name = "INSTRUCTOR_ID"), 
    inverseJoinColumns = @JoinColumn(name = "BATCH_ID"))
    private Set<Batch> batches = new HashSet<>();

}
