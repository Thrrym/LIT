package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Block;
import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.storage.IBlockRepository;
import de.tuberlin.tkn.lit.storage.ICreateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XBlockService implements IBlockService {

    @Override
    public IBlockRepository getRepository() {
        return repository;
    }

    @Autowired
    private IBlockRepository repository;

    @Override
    public List<Block> findAll() {

        return (List<Block>) repository.findAll();
    }

}
