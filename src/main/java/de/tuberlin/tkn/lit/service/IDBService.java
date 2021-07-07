package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.City;
import de.tuberlin.tkn.lit.storage.ICityRepository;
import de.tuberlin.tkn.lit.storage.IPersonRepository;

import java.util.List;

public interface IDBService {

    List<Person> findAllPersons();
    List<City> findAllCities();

    IPersonRepository getPersonRepository();
    ICityRepository getCityRepository();
}
