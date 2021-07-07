package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Country;
import de.tuberlin.tkn.lit.storage.ICountryRepository;

import java.util.List;

public interface ICountryService {

    List<Country> findAll();

    ICountryRepository getRepository();
}
