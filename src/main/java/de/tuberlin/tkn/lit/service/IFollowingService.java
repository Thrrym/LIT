package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IFollowingRepository;
import de.tuberlin.tkn.lit.storage.ILikedRepository;

import java.util.List;

public interface IFollowingService {

    List<LinkOrObject> findAll();

    IFollowingRepository getRepository();
}
