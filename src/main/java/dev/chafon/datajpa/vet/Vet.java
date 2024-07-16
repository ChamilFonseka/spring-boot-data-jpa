package dev.chafon.datajpa.vet;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.pet.Pet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vets")
@DynamicUpdate
public class Vet extends BaseEntity {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToMany(mappedBy = "vets")
    private final Set<Pet> pets = new LinkedHashSet<>();

    public static Vet of(VetDto vetDto) {
        return Vet.builder()
                .firstName(vetDto.firstName())
                .lastName(vetDto.lastName())
                .phoneNumber(vetDto.phoneNumber())
                .build();
    }

    public void update(VetDto vetDto) {
        this.firstName = vetDto.firstName();
        this.lastName = vetDto.lastName();
        this.phoneNumber = vetDto.phoneNumber();
    }

    @Override
    public String toString() {
        return "Vet{"
                + "firstName='"
                + firstName
                + '\''
                + ", lastName='"
                + lastName
                + '\''
                + ", phoneNumber='"
                + phoneNumber
                + '\''
                + '}';
    }
}
