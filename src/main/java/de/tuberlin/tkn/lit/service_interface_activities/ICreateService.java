package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.storage_activities.ICreateRepository;

import java.util.List;

public interface ICreateService {

    List<Create> findAll();

    ICreateRepository getRepository();
}
