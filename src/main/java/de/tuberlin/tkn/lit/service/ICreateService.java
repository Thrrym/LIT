package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.storage.IBookRepository;
import de.tuberlin.tkn.lit.storage.ICreateRepository;

import java.util.List;

public interface ICreateService {

    List<Create> findAll();

    ICreateRepository getRepository();
}
