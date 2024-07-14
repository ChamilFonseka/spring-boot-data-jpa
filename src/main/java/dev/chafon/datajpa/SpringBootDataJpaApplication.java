package dev.chafon.datajpa;

import dev.chafon.datajpa.owner.Address;
import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerRepository;
import dev.chafon.datajpa.pet.PetRepository;
import dev.chafon.datajpa.pet.PetType;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.dog.Dog;
import net.datafaker.Faker;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootDataJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataJpaApplication.class, args);
  }

  @Bean
  @Profile("dev")
  ApplicationRunner applicationRunner(
      PetRepository petRepository, OwnerRepository ownerRepository) {
    return args -> {
      Faker faker = new Faker();

      Owner owner =
          ownerRepository.save(
              Owner.builder()
                  .firstName(faker.name().firstName())
                  .lastName(faker.name().lastName())
                  .phoneNumber(faker.phoneNumber().phoneNumber())
                  .address(
                      new Address(
                          faker.address().streetAddress(),
                          faker.address().city(),
                          faker.address().state(),
                          faker.address().zipCode()))
                  .build());

      petRepository.save(
          Cat.builder()
              .name(faker.cat().name())
              .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
              .breed(faker.cat().breed())
              .registry(faker.cat().registry())
              .type(PetType.CAT)
              .owner(owner)
              .build());

      petRepository.save(
          Dog.builder()
              .name(faker.dog().name())
              .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
              .breed(faker.dog().breed())
              .size(faker.dog().size())
              .sound(faker.dog().sound())
              .coatLength(faker.dog().coatLength())
              .type(PetType.DOG)
              .owner(owner)
              .build());
    };
  }
}
