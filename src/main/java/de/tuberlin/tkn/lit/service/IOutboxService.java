package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.ILikedRepository;
import de.tuberlin.tkn.lit.storage.IOutboxRepository;

import java.util.List;

public interface IOutboxService {

    List<LinkOrObject> findAll();

    IOutboxRepository getRepository();
}
