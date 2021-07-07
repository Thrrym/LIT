package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBibTeXArticleRepository extends CrudRepository<BibTeXArticle, Long> {
}
