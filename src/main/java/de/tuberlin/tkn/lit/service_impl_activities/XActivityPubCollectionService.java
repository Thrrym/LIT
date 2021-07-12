package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.service_interface_litobjects.IActivityPubCollectionService;
import de.tuberlin.tkn.lit.storage_litobjects.IActivityPubCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XActivityPubCollectionService implements IActivityPubCollectionService {

    @Override
    public IActivityPubCollectionRepository getRepository() {
        return repository;
    }

    @Autowired
    private IActivityPubCollectionRepository repository;

    @Override
    public List<ActivityPubCollection> findAll() {

        return (List<ActivityPubCollection>) repository.findAll();
    }
}