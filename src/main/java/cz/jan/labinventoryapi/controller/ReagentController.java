package cz.jan.labinventoryapi.controller;

import cz.jan.labinventoryapi.dto.ReagentRequest;
import cz.jan.labinventoryapi.dto.ReagentResponse;
import cz.jan.labinventoryapi.model.Reagent;
import cz.jan.labinventoryapi.service.ReagentService;
import jakarta.validation.Valid;
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
    public List<ReagentResponse> getAll() {
        return service.findAll().stream().map(ReagentResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ReagentResponse getById(@PathVariable Long id) {
        return ReagentResponse.from(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ReagentResponse> create(@Valid @RequestBody ReagentRequest request) {
        Reagent created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReagentResponse.from(created));
    }

    @PutMapping("/{id}")
    public ReagentResponse update(@PathVariable Long id, @Valid @RequestBody ReagentRequest request) {
        return ReagentResponse.from(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)                  // vždy 204 → @ResponseStatus + void
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}