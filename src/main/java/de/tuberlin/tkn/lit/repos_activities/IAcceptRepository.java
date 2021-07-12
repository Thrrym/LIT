package de.tuberlin.tkn.lit.repos_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Accept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAcceptRepository extends CrudRepository<Accept, Long> {

}
