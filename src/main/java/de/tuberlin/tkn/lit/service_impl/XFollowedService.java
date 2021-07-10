package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.service.IFollowedService;
import de.tuberlin.tkn.lit.storage.IFollowedRepository;
import de.tuberlin.tkn.lit.storage.ILikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XFollowedService implements IFollowedService {

    @Override
    public IFollowedRepository getRepository() {
        return repository;
    }

    @Autowired
    private IFollowedRepository repository;

    @Override
    public List<LinkOrObject> findAll() {

        return (List<LinkOrObject>) repository.findAll();
    }
}
