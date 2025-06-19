package com.example.petstore.service;

import com.example.petstore.model.Pet;
import com.example.petstore.model.PetStatus;
import com.example.petstore.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    public void delete(Long id) {
        petRepository.deleteById(id);
    }

    public List<Pet> findByStatus(PetStatus status) {
        return petRepository.findByStatus(status);
    }

    public List<Pet> findByTags(List<String> tags) {
        return petRepository.findByTags_NameIn(tags);
    }
}
