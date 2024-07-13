package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
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

  public void update(PetDto petDto) {
    this.name = petDto.name();
    this.dateOfBirth = petDto.dateOfBirth();
    this.breed = petDto.breed();
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
