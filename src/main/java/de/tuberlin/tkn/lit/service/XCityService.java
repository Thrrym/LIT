package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.model.lit.City;
import de.tuberlin.tkn.lit.storage.IBookRepository;
import de.tuberlin.tkn.lit.storage.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XCityService implements ICityService{

    @Override
    public ICityRepository getRepository() {
        return repository;
    }

    @Autowired
    private ICityRepository repository;

    @Override
    public List<City> findAll() {

        return (List<City>) repository.findAll();
    }
}
