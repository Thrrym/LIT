package de.tuberlin.tkn.lit.service;


import de.tuberlin.tkn.lit.model.activitypub.activities.Update;
import de.tuberlin.tkn.lit.storage.IUpdateRepository;

import java.util.List;

public interface IUpdateService {

    List <Update> findAll();

    IUpdateRepository getRepository();

}
