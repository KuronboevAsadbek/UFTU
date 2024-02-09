package uz.uftu.ls.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import uz.uftu.ls.domain.enumeration.Role;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @NotNull
    @Size(min = 4, max = 50)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false, updatable = false)
    private Role role;

    @CurrentTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false, insertable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", nullable = false, insertable = false)
    private Boolean isDeleted;

    @ManyToOne
    private FieldOfStudy fieldOfStudy;

    @ManyToOne
    private FileStorage fileStorage;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "address")
    private String address;

    @Column(name = "number_of_personal_document")
    private String numberOfPersonalDocument;

    @Column(name = "сontact_number")
    private String contactNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_payments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id", unique = true))
    private Set<Payment> payments;


}
