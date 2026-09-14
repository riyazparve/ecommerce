package com.riyaz.ecom.jpademo.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LEARNER")
public class Learner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "CURRENT_BATCH_ID")
    private Batch currentBatch;

    @ManyToMany
    @JoinTable(name = "LEARNERS_PREVIOUS_BATCHES", 
    joinColumns = @JoinColumn(name = "LEARNER_ID"), 
    inverseJoinColumns = @JoinColumn(name = "PREVIOUS_BATCH_ID"))
    private Set<Batch> previousBatches = new HashSet<>();
}
