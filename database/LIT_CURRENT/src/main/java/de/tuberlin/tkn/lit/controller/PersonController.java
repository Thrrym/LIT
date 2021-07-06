package de.tuberlin.tkn.lit.controller;


import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.service.IPersonService;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.storage.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private IPersonService personService;

    @Autowired
    IStorage storage;

    @CrossOrigin
    @GetMapping("/db/all/persons")
    public List<Person> findAll() {
        PersonRepository repo = personService.getRepository();
        List<Person> personsList = (List<Person>) repo.findAll();
        for(Person p : (List<Person>) repo.findAll()) {
            this.storage.createActor(p);
        }
        return personsList;
    }
}