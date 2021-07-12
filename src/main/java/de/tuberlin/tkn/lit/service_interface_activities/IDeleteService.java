package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Delete;
import de.tuberlin.tkn.lit.repos_activities.IDeleteRepository;

import java.util.List;

public interface IDeleteService {

    List <Delete> findAll();

    IDeleteRepository getRepository();
}
