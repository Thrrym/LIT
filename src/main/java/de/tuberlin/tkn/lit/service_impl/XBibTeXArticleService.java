package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.service.IBibTeXArticleService;
import de.tuberlin.tkn.lit.storage.IBibTeXArticleRepository;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XBibTeXArticleService implements IBibTeXArticleService {

    @Autowired
    private IBibTeXArticleRepository repository;

    @Override
    public IBibTeXArticleRepository getRepository() {
        return repository;
    }

    @Override
    public List<BibTeXArticle> findAll() {

        return (List<BibTeXArticle>) repository.findAll();
    }
}
