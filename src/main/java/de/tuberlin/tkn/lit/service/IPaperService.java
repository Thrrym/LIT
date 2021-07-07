package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.Paper;
import de.tuberlin.tkn.lit.storage.IPaperRepository;
import de.tuberlin.tkn.lit.storage.IPersonRepository;

import java.util.List;

public interface IPaperService {

    List<Paper> findAll();

    IPaperRepository getRepository();
}
