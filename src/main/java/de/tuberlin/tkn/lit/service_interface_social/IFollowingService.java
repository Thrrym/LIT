package de.tuberlin.tkn.lit.service_interface_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Following;
import de.tuberlin.tkn.lit.storage_social.IFollowingRepository;

import java.util.List;

public interface IFollowingService {

    List<Following> findAll();

    IFollowingRepository getRepository();
}
