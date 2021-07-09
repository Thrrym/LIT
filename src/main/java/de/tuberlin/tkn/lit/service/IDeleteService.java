package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.model.activitypub.activities.Delete;
import de.tuberlin.tkn.lit.storage.ICreateRepository;
import de.tuberlin.tkn.lit.storage.IDeleteRepository;

import java.util.List;

public interface IDeleteService {

    List <Delete> findAll();

    IDeleteRepository getRepository();
}
