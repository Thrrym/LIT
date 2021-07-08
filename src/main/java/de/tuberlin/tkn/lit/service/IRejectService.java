package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Reject;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import de.tuberlin.tkn.lit.storage.IRejectRepository;

import java.util.List;

public interface IRejectService {

    List<Reject> findAll();

    IRejectRepository getRepository();
}
