package dev.chafon.datajpa.pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dogs")
public class Dog extends Pet {
  @Column(nullable = false)
  private String sound;

  @Column(nullable = false)
  private String size;

  @Column(nullable = false)
  private String coatLength;
}
