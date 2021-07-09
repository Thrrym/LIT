package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Ignore;
import de.tuberlin.tkn.lit.storage.IIgnoreRepository;

import java.util.List;

public interface IIgnoreService {

    List<Ignore> findAll();

    IIgnoreRepository getRepository();

}
