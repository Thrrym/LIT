package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.storage.IAuthorRepository;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements IAuthorService{

    @Override
    public IAuthorRepository getRepository() {
        return repository;
    }

    @Autowired
    private IAuthorRepository repository;


    @Override
    public List<Author> findAll() {

        return (List<Author>) repository.findAll();
    }
}
