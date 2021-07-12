package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.repos_litobjects.IBookRepository;

import java.util.List;

public interface IBookService {

    List<Book> findAll();

    IBookRepository getRepository();
}
