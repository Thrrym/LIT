package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.storage_litobjects.IAuthorRepository;

import java.util.List;

public interface IAuthorService {

    List<Author> findAll();

    IAuthorRepository getRepository();
}
