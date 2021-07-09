package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Accept;
import de.tuberlin.tkn.lit.storage.IAcceptRepository;

import java.util.List;

public interface IAcceptService {
    List <Accept> findAll();

    IAcceptRepository getRepository();

}
