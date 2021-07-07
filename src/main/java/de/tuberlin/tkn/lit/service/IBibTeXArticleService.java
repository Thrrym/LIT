package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.storage.IBibTeXArticleRepository;
import de.tuberlin.tkn.lit.storage.IPersonRepository;

import java.util.List;

public interface IBibTeXArticleService {

    List<BibTeXArticle> findAll();

    IBibTeXArticleRepository getRepository();
}
