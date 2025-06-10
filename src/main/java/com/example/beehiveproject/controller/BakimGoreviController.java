package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.BakimGorevi;
import com.example.beehiveproject.service.BakimGoreviService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bakim-gorevleri")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class BakimGoreviController {
    private final BakimGoreviService service;

    public BakimGoreviController(BakimGoreviService service) {
        this.service = service;
    }

    @GetMapping
    public List<BakimGorevi> getAll() {
        return service.getAll();
    }

    @PostMapping
    public BakimGorevi create(@RequestBody BakimGorevi gorev) {
        return service.save(gorev);
    }

    @PutMapping("/{id}")
    public BakimGorevi update(@PathVariable Long id, @RequestBody BakimGorevi gorev) {
        return service.update(id, gorev);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/colony/{koloniId}")
    public BakimGorevi createForColony(@PathVariable Long koloniId, @RequestBody BakimGorevi gorev) {
        return service.createForColony(koloniId, gorev);
    }

    @PostMapping("/hive/{kovanId}")
    public BakimGorevi createForHive(@PathVariable Long kovanId, @RequestBody BakimGorevi gorev) {
        return service.createForHive(kovanId, gorev);
    }

    @PatchMapping("/{id}/tamamla")
    public BakimGorevi markAsCompleted(@PathVariable Long id) {
        return service.markAsCompleted(id);
    }
} 