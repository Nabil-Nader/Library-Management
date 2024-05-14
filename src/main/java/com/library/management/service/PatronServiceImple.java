package com.library.management.service;

import com.library.management.model.Patron;
import com.library.management.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronServiceImple implements   PatronService{

    private final PatronRepository patronRepository ;

    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron getPatronById(Long id) {
        Optional<Patron> patron = patronRepository.findById(id);
        if (patron.isPresent()) {
            return patron.get();
        } else {
            throw new RuntimeException("Patron not found for id: " + id);
        }
    }

    @Override
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    public Patron updatePatron(Long id, Patron patronDetails) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        if (optionalPatron.isPresent()) {
            Patron patron = optionalPatron.get();
            patron.setName(patronDetails.getName());
            patron.setContactInformation(patronDetails.getContactInformation());
            // update other fields as necessary
            return patronRepository.save(patron);
        } else {
            throw new RuntimeException("Patron not found for id: " + id);
        }
    }

    @Override
    public void deletePatron(Long id) {
        if (patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
        } else {
            throw new RuntimeException("Patron not found for id: " + id);
        }
    }
}
