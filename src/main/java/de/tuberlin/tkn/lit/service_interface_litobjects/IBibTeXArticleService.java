package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.repos_litobjects.IBibTeXArticleRepository;

import java.util.List;

public interface IBibTeXArticleService {

    List<BibTeXArticle> findAll();

    IBibTeXArticleRepository getRepository();
}
