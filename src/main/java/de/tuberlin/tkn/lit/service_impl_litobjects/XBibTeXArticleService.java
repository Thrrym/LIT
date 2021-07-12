package de.tuberlin.tkn.lit.service_impl_litobjects;

import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.service_interface_litobjects.IBibTeXArticleService;
import de.tuberlin.tkn.lit.repos_litobjects.IBibTeXArticleRepository;
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
