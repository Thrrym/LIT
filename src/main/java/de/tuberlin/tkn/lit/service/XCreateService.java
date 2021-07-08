package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.model.lit.Journal;
import de.tuberlin.tkn.lit.storage.ICreateRepository;
import de.tuberlin.tkn.lit.storage.IJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XCreateService implements ICreateService{

    @Override
    public ICreateRepository getRepository() {
        return repository;
    }

    @Autowired
    private ICreateRepository repository;

    @Override
    public List<Create> findAll() {

        return (List<Create>) repository.findAll();
    }
}
