package de.tuberlin.tkn.lit.service_impl_litobjects;

import de.tuberlin.tkn.lit.model.lit.Journal;
import de.tuberlin.tkn.lit.service_interface_litobjects.IJournalService;
import de.tuberlin.tkn.lit.repos_litobjects.IJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XJournalService implements IJournalService {

    @Override
    public IJournalRepository getRepository() {
        return repository;
    }

    @Autowired
    private IJournalRepository repository;

    @Override
    public List<Journal> findAll() {

        return (List<Journal>) repository.findAll();
    }
}
