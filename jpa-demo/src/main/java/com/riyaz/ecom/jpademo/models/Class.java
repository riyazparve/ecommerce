package com.riyaz.ecom.jpademo.models;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CLASS")
public class Class {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TOPIC")
    private String topic;
    
    @ManyToMany(mappedBy = "classes")
    private Set<Batch> batches;

    @ManyToOne
    @JoinColumn(name = "CURRENT_INSTRUCTOR_ID")
    private Instructor currentInstructor;
}
