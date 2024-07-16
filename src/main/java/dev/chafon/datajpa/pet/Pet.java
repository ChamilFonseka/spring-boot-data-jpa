package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.vet.Vet;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pets")
@DynamicUpdate
public abstract class Pet extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Owner owner;

    @ManyToMany
    @JoinTable(
            name = "pets_vets",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_id"))
    @Builder.Default
    private Set<Vet> vets = new LinkedHashSet<>();

    public void update(PetDto petDto, Owner owner, Set<Vet> vets) {
        this.name = petDto.name();
        this.dateOfBirth = petDto.dateOfBirth();
        this.breed = petDto.breed();
        this.type = petDto.type();
        this.owner = owner;
        this.vets = vets;
    }

    @Override
    public String toString() {
        return "Pet{"
                + "id='"
                + getId()
                + '\''
                + "name='"
                + name
                + '\''
                + ", dateOfBirth="
                + dateOfBirth
                + ", breed='"
                + breed
                + '\''
                + ", type="
                + type
                + '}';
    }
}
