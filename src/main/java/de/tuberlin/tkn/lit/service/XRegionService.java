package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.Region;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import de.tuberlin.tkn.lit.storage.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class XRegionService implements IRegionService{

    @Override
    public IRegionRepository getRepository() {
        return repository;
    }

    @Autowired
    private IRegionRepository repository;

    @Override
    public List<Region> findAll() {

        return (List<Region>) repository.findAll();
    }
}
