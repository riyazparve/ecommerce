package com.riyaz.ecom.jpademo.models;

import java.util.HashSet;
import java.util.List;
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
@Table(name = "BATCH")
public class Batch {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;
    
    @ManyToMany(mappedBy = "batches")
    private List<Instructor> instructors;

    @ManyToMany(mappedBy = "currentBatch")
    private Set<Learner> learners = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "BATCHES_CLASSES",
    joinColumns = @JoinColumn(name="BATCH_ID"),
    inverseJoinColumns = @JoinColumn(name="CLASS_ID"))
    private Set<Class> classes;
}
