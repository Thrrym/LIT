package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.lit.Journal;
import de.tuberlin.tkn.lit.repos_litobjects.IJournalRepository;

import java.util.List;

public interface IJournalService {

    List<Journal> findAll();

    IJournalRepository getRepository();
}
