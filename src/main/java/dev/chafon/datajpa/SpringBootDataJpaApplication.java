package dev.chafon.datajpa;

import dev.chafon.datajpa.owner.Address;
import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerRepository;
import dev.chafon.datajpa.pet.Pet;
import dev.chafon.datajpa.pet.PetRepository;
import dev.chafon.datajpa.pet.PetType;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.vet.Vet;
import dev.chafon.datajpa.vet.VetRepository;
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
            PetRepository petRepository, OwnerRepository ownerRepository, VetRepository vetRepository) {
        return args -> {
            Faker faker = new Faker();

            Owner owner1 = ownerRepository.save(Owner.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .address(new Address(
                            faker.address().streetAddress(),
                            faker.address().city(),
                            faker.address().state(),
                            faker.address().zipCode()))
                    .build());

            Owner owner2 = ownerRepository.save(Owner.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .address(new Address(
                            faker.address().streetAddress(),
                            faker.address().city(),
                            faker.address().state(),
                            faker.address().zipCode()))
                    .build());

            Pet owner1Cat = petRepository.save(Cat.builder()
                    .name(faker.cat().name())
                    .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
                    .breed(faker.cat().breed())
                    .registry(faker.cat().registry())
                    .type(PetType.CAT)
                    .owner(owner1)
                    .build());

            Pet owner1Dog = petRepository.save(Dog.builder()
                    .name(faker.dog().name())
                    .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
                    .breed(faker.dog().breed())
                    .size(faker.dog().size())
                    .sound(faker.dog().sound())
                    .coatLength(faker.dog().coatLength())
                    .type(PetType.DOG)
                    .owner(owner1)
                    .build());

            Pet owner2Cat = petRepository.save(Cat.builder()
                    .name(faker.cat().name())
                    .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
                    .breed(faker.cat().breed())
                    .registry(faker.cat().registry())
                    .type(PetType.CAT)
                    .owner(owner2)
                    .build());

            Pet owner2Dog = petRepository.save(Dog.builder()
                    .name(faker.dog().name())
                    .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
                    .breed(faker.dog().breed())
                    .size(faker.dog().size())
                    .sound(faker.dog().sound())
                    .coatLength(faker.dog().coatLength())
                    .type(PetType.DOG)
                    .owner(owner2)
                    .build());

            Vet vet1 = vetRepository.save(Vet.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .build());

            Vet vet2 = vetRepository.save(Vet.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .build());

            owner1Cat.getVets().add(vet1);
            owner1Cat.getVets().add(vet2);
            petRepository.save(owner1Cat);

            owner1Dog.getVets().add(vet1);
            petRepository.save(owner1Dog);

            owner2Dog.getVets().add(vet1);
            owner2Dog.getVets().add(vet2);
            petRepository.save(owner2Dog);
        };
    }
}
