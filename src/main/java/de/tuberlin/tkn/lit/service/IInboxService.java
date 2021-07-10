package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IInboxRepository;
import de.tuberlin.tkn.lit.storage.ILikedRepository;

import java.util.List;

public interface IInboxService {

    List<LinkOrObject> findAll();

    IInboxRepository getRepository();
}
