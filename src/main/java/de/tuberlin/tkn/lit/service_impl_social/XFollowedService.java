package de.tuberlin.tkn.lit.service_impl_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Followed;
import de.tuberlin.tkn.lit.service_interface_social.IFollowedService;
import de.tuberlin.tkn.lit.storage_social.IFollowedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XFollowedService implements IFollowedService {

    @Override
    public IFollowedRepository getRepository() {
        return repository;
    }

    @Autowired
    private IFollowedRepository repository;

    @Override
    public List<Followed> findAll() {

        return (List<Followed>) repository.findAll();
    }
}
