package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Dislike;
import de.tuberlin.tkn.lit.storage.IDislikeRepository;

import java.util.List;

public interface IDislikeService {

    List<Dislike> findAll();

    IDislikeRepository getRepository();

}
