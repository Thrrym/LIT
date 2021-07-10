package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.service.IAuthorService;
import de.tuberlin.tkn.lit.storage.IAuthorRepository;
import de.tuberlin.tkn.lit.storage.IBibTeXArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XAuthorService implements IAuthorService {

    @Autowired
    private IAuthorRepository repository;

    @Override
    public IAuthorRepository getRepository() {
        return repository;
    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) repository.findAll();
    }
}
