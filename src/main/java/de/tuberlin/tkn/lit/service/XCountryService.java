package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.City;
import de.tuberlin.tkn.lit.model.lit.Country;
import de.tuberlin.tkn.lit.storage.ICityRepository;
import de.tuberlin.tkn.lit.storage.ICountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XCountryService implements ICountryService{

    @Override
    public ICountryRepository getRepository() {
        return repository;
    }

    @Autowired
    private ICountryRepository repository;

    @Override
    public List<Country> findAll() {

        return (List<Country>) repository.findAll();
    }
}
