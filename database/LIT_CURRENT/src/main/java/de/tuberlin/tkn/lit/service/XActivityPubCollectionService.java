package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.storage.ActivityPubCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XActivityPubCollectionService implements IActivityPubCollectionService {

    @Override
    public ActivityPubCollectionRepository getRepository() {
        return repository;
    }

    @Autowired
    private ActivityPubCollectionRepository repository;

    @Override
    public List<ActivityPubCollection> findAll() {

        return (List<ActivityPubCollection>) repository.findAll();
    }
}