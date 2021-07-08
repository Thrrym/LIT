package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Like;
import de.tuberlin.tkn.lit.model.lit.Journal;
import de.tuberlin.tkn.lit.storage.IJournalRepository;
import de.tuberlin.tkn.lit.storage.ILikeRepository;

import java.util.List;

public interface ILikeService {

    List<Like> findAll();

    ILikeRepository getRepository();
}
