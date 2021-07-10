package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.activities.Dislike;
import de.tuberlin.tkn.lit.service.IDislikeService;
import de.tuberlin.tkn.lit.storage.IDislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XDislikeService implements IDislikeService {

    @Override
    public IDislikeRepository getRepository() {
        return repository;
    }

    @Autowired
    private IDislikeRepository repository;

    @Override
    public List<Dislike> findAll() {

        return (List<Dislike>) repository.findAll();
    }
}
