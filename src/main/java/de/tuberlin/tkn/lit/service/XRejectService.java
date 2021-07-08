package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Reject;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import de.tuberlin.tkn.lit.storage.IRejectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class XRejectService implements IRejectService{

    @Override
    public IRejectRepository getRepository() {
        return repository;
    }

    @Autowired
    private IRejectRepository repository;

    @Override
    public List<Reject> findAll() {

        return (List<Reject>) repository.findAll();
    }
}
