package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Undo;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import de.tuberlin.tkn.lit.storage.IUndoRepository;

import java.util.List;

public interface IUndoService {

    List<Undo> findAll();

    IUndoRepository getRepository();
}
