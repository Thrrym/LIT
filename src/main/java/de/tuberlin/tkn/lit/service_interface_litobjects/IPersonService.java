package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.repos_litobjects.IPersonRepository;

import java.util.List;

public interface IPersonService {

    List<Person> findAll();

    IPersonRepository getRepository();
}