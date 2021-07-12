package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Reject;
import de.tuberlin.tkn.lit.repos_activities.IRejectRepository;

import java.util.List;

public interface IRejectService {

    List<Reject> findAll();

    IRejectRepository getRepository();
}
