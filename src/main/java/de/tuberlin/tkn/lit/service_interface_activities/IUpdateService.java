package de.tuberlin.tkn.lit.service_interface_activities;


import de.tuberlin.tkn.lit.model.activitypub.activities.Update;
import de.tuberlin.tkn.lit.storage_activities.IUpdateRepository;

import java.util.List;

public interface IUpdateService {

    List <Update> findAll();

    IUpdateRepository getRepository();

}
