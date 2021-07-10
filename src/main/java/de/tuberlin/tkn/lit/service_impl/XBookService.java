package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.service.IBookService;
import de.tuberlin.tkn.lit.storage.IActivityPubCollectionRepository;
import de.tuberlin.tkn.lit.storage.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class XBookService implements IBookService {

    @Override
    public IBookRepository getRepository() {
        return repository;
    }

    @Autowired
    private IBookRepository repository;

    @Override
    public List<Book> findAll() {

        return (List<Book>) repository.findAll();
    }
}
