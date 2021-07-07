package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.storage.IPersonRepository;

import java.util.List;

public interface IPersonService {

    List<Person> findAll();

    IPersonRepository getRepository();
}