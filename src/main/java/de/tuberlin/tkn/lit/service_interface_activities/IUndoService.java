package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Undo;
import de.tuberlin.tkn.lit.repos_activities.IUndoRepository;

import java.util.List;

public interface IUndoService {

    List<Undo> findAll();

    IUndoRepository getRepository();
}
