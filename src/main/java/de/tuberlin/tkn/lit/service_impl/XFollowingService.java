package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.service.IFollowingService;
import de.tuberlin.tkn.lit.storage.IFollowedRepository;
import de.tuberlin.tkn.lit.storage.IFollowingRepository;
import de.tuberlin.tkn.lit.storage.ILikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XFollowingService implements IFollowingService {

    @Override
    public IFollowingRepository getRepository() {
        return repository;
    }

    @Autowired
    private IFollowingRepository repository;

    @Override
    public List<LinkOrObject> findAll() {

        return (List<LinkOrObject>) repository.findAll();
    }
}
