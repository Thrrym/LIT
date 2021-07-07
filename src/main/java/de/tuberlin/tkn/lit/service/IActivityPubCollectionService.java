package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.storage.IActivityPubCollectionRepository;

import java.util.List;

public interface IActivityPubCollectionService {

    List<ActivityPubCollection> findAll();

    IActivityPubCollectionRepository getRepository();
}