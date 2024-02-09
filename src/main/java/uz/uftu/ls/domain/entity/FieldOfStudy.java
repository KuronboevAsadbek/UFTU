package uz.uftu.ls.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "field_of_study")
@AllArgsConstructor
@NoArgsConstructor

public class FieldOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted;

    @ManyToOne
    private Faculty faculty;

    @ManyToMany
    @JoinTable(name = "field_of_study_science",
            joinColumns = @JoinColumn(name = "field_of_study_id"),
            inverseJoinColumns = @JoinColumn(name = "science_id"))
    private Set<Science> sciences = new HashSet<>();


}