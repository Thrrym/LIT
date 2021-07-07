package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Paper;
import de.tuberlin.tkn.lit.model.lit.Region;
import de.tuberlin.tkn.lit.storage.IPaperRepository;
import de.tuberlin.tkn.lit.storage.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XPaperService implements IPaperService{

    @Override
    public IPaperRepository getRepository() {
        return repository;
    }

    @Autowired
    private IPaperRepository repository;

    @Override
    public List<Paper> findAll() {

        return (List<Paper>) repository.findAll();
    }
}
