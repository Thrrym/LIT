package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.storage.IBookRepository;

import java.util.List;

public interface IBookService {

    List<Book> findAll();

    IBookRepository getRepository();
}
