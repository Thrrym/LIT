package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.storage_litobjects.IActivityPubCollectionRepository;

import java.util.List;

public interface IActivityPubCollectionService {

    List<ActivityPubCollection> findAll();

    IActivityPubCollectionRepository getRepository();
}