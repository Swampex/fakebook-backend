package ru.fakebook.pet.model;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @EqualsAndHashCode.Exclude
    @ElementCollection(fetch = FetchType.EAGER)
    private List<User> follows;

    private String name;
    private String firstName;
    private String email;

    @Column(unique = true)
    private String login;
    private String hashPassword;

    @Column(length = 1000)
    private String photo;

    private String status;

    @Embedded
    private UserLocation userLocation;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_id_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Transient
    Boolean followed = false;
}
