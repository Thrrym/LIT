package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Ignore;
import de.tuberlin.tkn.lit.storage_activities.IIgnoreRepository;

import java.util.List;

public interface IIgnoreService {

    List<Ignore> findAll();

    IIgnoreRepository getRepository();

}
