package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.ILikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XFollowingService implements IFollowingService{

    @Override
    public ILikedRepository getRepository() {
        return repository;
    }

    @Autowired
    private ILikedRepository repository;

    @Override
    public List<LinkOrObject> findAll() {

        return (List<LinkOrObject>) repository.findAll();
    }
}
