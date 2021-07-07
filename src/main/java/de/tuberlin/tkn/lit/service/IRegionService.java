package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.City;
import de.tuberlin.tkn.lit.model.lit.Region;
import de.tuberlin.tkn.lit.storage.ICityRepository;
import de.tuberlin.tkn.lit.storage.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IRegionService {

    List<Region> findAll();

    IRegionRepository getRepository();
}
