package de.tuberlin.tkn.lit.service_interface_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Followed;
import de.tuberlin.tkn.lit.storage_social.IFollowedRepository;

import java.util.List;

public interface IFollowedService {

    List<Followed> findAll();

    IFollowedRepository getRepository();
}
