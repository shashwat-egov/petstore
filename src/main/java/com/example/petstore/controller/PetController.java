package com.example.petstore.controller;

import com.example.petstore.model.Pet;
import com.example.petstore.model.PetStatus;
import com.example.petstore.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet")
@Validated
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> addPet(@Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.save(pet));
    }

    @PutMapping
    public ResponseEntity<Pet> updatePet(@Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.save(pet));
    }

    @GetMapping("/findByStatus")
    public List<Pet> findByStatus(@RequestParam(defaultValue = "available") PetStatus status) {
        return petService.findByStatus(status);
    }

    @GetMapping("/findByTags")
    public List<Pet> findByTags(@RequestParam List<String> tags) {
        return petService.findByTags(tags);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long petId) {
        return petService.findById(petId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{petId}")
    public ResponseEntity<Pet> updatePetWithForm(@PathVariable Long petId,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) PetStatus status) {
        Optional<Pet> petOpt = petService.findById(petId);
        if (petOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pet pet = petOpt.get();
        if (name != null) pet.setName(name);
        if (status != null) pet.setStatus(status);
        return ResponseEntity.ok(petService.save(pet));
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.delete(petId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{petId}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable Long petId,
                                              @RequestParam(required = false) String additionalMetadata,
                                              @RequestParam MultipartFile file) {
        // For simplicity we don't store the file
        return ResponseEntity.ok("uploaded");
    }
}
