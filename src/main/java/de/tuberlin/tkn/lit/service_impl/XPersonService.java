package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.service.IPersonService;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XPersonService implements IPersonService {

    @Override
    public IPersonRepository getRepository() {
        return repository;
    }

    @Autowired
    private IPersonRepository repository;


    @Override
    public List<Person> findAll() {

        return (List<Person>) repository.findAll();
    }
}