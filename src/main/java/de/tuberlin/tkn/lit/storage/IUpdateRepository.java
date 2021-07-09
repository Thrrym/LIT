package de.tuberlin.tkn.lit.storage;
import de.tuberlin.tkn.lit.model.activitypub.activities.Update;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUpdateRepository extends CrudRepository<Update, Long>{
}
