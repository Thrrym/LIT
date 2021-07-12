package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Block;
import de.tuberlin.tkn.lit.service_interface_activities.IBlockService;
import de.tuberlin.tkn.lit.storage_activities.IBlockRepository;
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
