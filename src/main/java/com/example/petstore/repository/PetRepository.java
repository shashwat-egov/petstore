package com.example.petstore.repository;

import com.example.petstore.model.Pet;
import com.example.petstore.model.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByStatus(PetStatus status);
    List<Pet> findByTags_NameIn(List<String> names);
}
