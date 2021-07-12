package de.tuberlin.tkn.lit.service_interface_social;

import de.tuberlin.tkn.lit.model.activitypub.social.RelevantObject;
import de.tuberlin.tkn.lit.storage_social.IRelevantObjectRepository;

import java.util.List;

public interface IRelevantObjectService {

    List<RelevantObject> findAll();

    IRelevantObjectRepository getRepository();
}
