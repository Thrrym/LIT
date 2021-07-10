package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IFollowedRepository;
import de.tuberlin.tkn.lit.storage.ILikedRepository;

import java.util.List;

public interface IFollowedService {

    List<LinkOrObject> findAll();

    IFollowedRepository getRepository();
}
