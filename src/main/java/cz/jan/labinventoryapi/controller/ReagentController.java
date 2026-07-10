package cz.jan.labinventoryapi.controller;

import cz.jan.labinventoryapi.dto.ReagentRequest;
import cz.jan.labinventoryapi.model.Reagent;
import cz.jan.labinventoryapi.service.ReagentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reagents")
public class ReagentController {

    private final ReagentService service;

    public ReagentController(ReagentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reagent> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reagent> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reagent> create(@RequestBody ReagentRequest request) {
        Reagent created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reagent> update(@PathVariable Long id, @RequestBody ReagentRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

/*
@GetMapping("/{id}")
public ResponseEntity<Reagent> getById(@PathVariable Long id) {
    Optional<Reagent> found = service.findById(id);

    if (found.isPresent()) {
        return ResponseEntity.ok(found.get());      // MÁM → 200 OK + reagent
    } else {
        return ResponseEntity.notFound().build();   // NEMÁM → 404 Not Found
    }
}
 */