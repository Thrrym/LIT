package de.tuberlin.tkn.lit.service_impl_social;

import de.tuberlin.tkn.lit.model.activitypub.social.RelevantObject;
import de.tuberlin.tkn.lit.storage_social.IRelevantObjectRepository;
import de.tuberlin.tkn.lit.service_interface_social.IRelevantObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XRelevantObjectService implements IRelevantObjectService {

    @Override
    public IRelevantObjectRepository getRepository() {
        return repository;
    }

    @Autowired
    private IRelevantObjectRepository repository;

    @Override
    public List<RelevantObject> findAll() {

        return (List<RelevantObject>) repository.findAll();
    }
}
