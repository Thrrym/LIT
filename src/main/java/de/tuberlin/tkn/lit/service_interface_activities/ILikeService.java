package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Like;
import de.tuberlin.tkn.lit.storage_activities.ILikeRepository;

import java.util.List;

public interface ILikeService {

    List<Like> findAll();

    ILikeRepository getRepository();
}
