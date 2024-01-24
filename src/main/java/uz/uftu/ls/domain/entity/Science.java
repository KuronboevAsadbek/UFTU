package uz.uftu.ls.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Science {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false", insertable = false)
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(name = "file_storage_science",
            joinColumns = @JoinColumn(name = "science_id"),
            inverseJoinColumns = @JoinColumn(name = "file_storage_id"))
    private Set<FileStorage> fileStorage = new HashSet<>();
}
