package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Follow;
import de.tuberlin.tkn.lit.storage.IFollowRepository;

import java.util.List;

public interface IFollowService {

    List<Follow> findAll();

    IFollowRepository getRepository();
}
