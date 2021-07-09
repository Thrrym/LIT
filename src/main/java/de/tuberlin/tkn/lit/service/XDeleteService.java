package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Delete;
import de.tuberlin.tkn.lit.storage.IDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XDeleteService implements IDeleteService {

    @Override
    public IDeleteRepository getRepository() {
        return repository;
    }

    @Autowired
    private IDeleteRepository repository;

    @Override
    public List<Delete> findAll() {

        return (List<Delete>) repository.findAll();
    }

}
