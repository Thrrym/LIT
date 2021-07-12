package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Dislike;
import de.tuberlin.tkn.lit.storage_activities.IDislikeRepository;

import java.util.List;

public interface IDislikeService {

    List<Dislike> findAll();

    IDislikeRepository getRepository();

}
