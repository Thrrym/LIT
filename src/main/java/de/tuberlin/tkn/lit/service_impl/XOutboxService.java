package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.service.IOutboxService;
import de.tuberlin.tkn.lit.storage.ILikedRepository;
import de.tuberlin.tkn.lit.storage.IOutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XOutboxService implements IOutboxService {

    @Override
    public IOutboxRepository getRepository() {
        return repository;
    }

    @Autowired
    private IOutboxRepository repository;

    @Override
    public List<LinkOrObject> findAll() {

        return (List<LinkOrObject>) repository.findAll();
    }
}
