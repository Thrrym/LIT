package de.tuberlin.tkn.lit.service_impl_litobjects;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.service_interface_litobjects.IPersonService;
import de.tuberlin.tkn.lit.storage_litobjects.IPersonRepository;
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