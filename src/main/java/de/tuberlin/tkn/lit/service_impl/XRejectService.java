package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.activities.Reject;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.service.IRejectService;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import de.tuberlin.tkn.lit.storage.IRejectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XRejectService implements IRejectService {

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
