package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.storage.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XPersonService implements IPersonService {

    @Override
    public PersonRepository getRepository() {
        return repository;
    }

    @Autowired
    private PersonRepository repository;


    @Override
    public List<Person> findAll() {

        return (List<Person>) repository.findAll();
    }
}