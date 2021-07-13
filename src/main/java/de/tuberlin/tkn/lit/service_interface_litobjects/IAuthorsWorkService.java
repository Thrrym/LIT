package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.model.lit.AuthorsWork;
import de.tuberlin.tkn.lit.storage_litobjects.IAuthorRepository;
import de.tuberlin.tkn.lit.storage_litobjects.IAuthorsWorkRepository;

import java.util.List;

public interface IAuthorsWorkService {

    List<AuthorsWork> findAll();

    IAuthorsWorkRepository getRepository();
}
