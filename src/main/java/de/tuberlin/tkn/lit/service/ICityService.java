package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.model.lit.City;
import de.tuberlin.tkn.lit.storage.IBookRepository;
import de.tuberlin.tkn.lit.storage.ICityRepository;

import java.util.List;

public interface ICityService {

    List<City> findAll();

    ICityRepository getRepository();
}
