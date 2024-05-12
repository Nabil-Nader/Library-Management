package com.library.management.service;

import com.library.management.exception.PatronNotFoundException;
import com.library.management.model.Patron;
import com.library.management.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronServiceImple implements PatronService{

    private final PatronRepository patronRepository;

    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + id));
    }

    @Override
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    public Patron updatePatron(Long id, Patron patron) {
        if (!patronRepository.existsById(id)) {
            throw new PatronNotFoundException("Patron not found with id: " + id);
        }
        patron.setId(id);
        return patronRepository.save(patron);
    }

    @Override
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new PatronNotFoundException("Patron not found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}
