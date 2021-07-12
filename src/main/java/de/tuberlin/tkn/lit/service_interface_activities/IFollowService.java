package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Follow;
import de.tuberlin.tkn.lit.storage_activities.IFollowRepository;

import java.util.List;

public interface IFollowService {

    List<Follow> findAll();

    IFollowRepository getRepository();
}
