package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.service.IInboxService;
import de.tuberlin.tkn.lit.storage.IInboxRepository;
import de.tuberlin.tkn.lit.storage.ILikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XInboxService implements IInboxService {

    @Override
    public IInboxRepository getRepository() {
        return repository;
    }

    @Autowired
    private IInboxRepository repository;

    @Override
    public List<LinkOrObject> findAll() {

        return (List<LinkOrObject>) repository.findAll();
    }
}
