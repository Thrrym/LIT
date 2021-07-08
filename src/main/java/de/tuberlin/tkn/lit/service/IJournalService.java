package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Journal;
import de.tuberlin.tkn.lit.storage.IJournalRepository;

import java.util.List;

public interface IJournalService {

    List<Journal> findAll();

    IJournalRepository getRepository();
}
