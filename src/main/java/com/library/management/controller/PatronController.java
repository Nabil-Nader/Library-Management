package com.library.management.controller;

import com.library.management.dto.Response;
import com.library.management.model.Patron;
import com.library.management.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
public class PatronController {


    private  final PatronService patronService;

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok().body(patrons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable("id") Long id) {
        Patron patron = patronService.getPatronById(id);
        return ResponseEntity.ok().body(patron);
    }

    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        Patron savedPatron = patronService.addPatron(patron);
        return new ResponseEntity<>(savedPatron, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable("id") Long id, @RequestBody Patron patron) {
        Patron updatedPatron = patronService.updatePatron(id, patron);
        return ResponseEntity.ok().body(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePatron(@PathVariable("id") Long id) {
        patronService.deletePatron(id);
        return new ResponseEntity<>(new Response<>("Patron deleted successfully", true, HttpStatus.OK.value()), HttpStatus.OK);
    }
}
