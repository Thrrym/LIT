package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Accept;
import de.tuberlin.tkn.lit.storage_activities.IAcceptRepository;

import java.util.List;

public interface IAcceptService {
    List <Accept> findAll();

    IAcceptRepository getRepository();

}
