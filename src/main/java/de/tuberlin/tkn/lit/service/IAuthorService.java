package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.storage.IAuthorRepository;

import java.util.List;

public interface IAuthorService {

    List<Author> findAll();

    IAuthorRepository getRepository();
}
