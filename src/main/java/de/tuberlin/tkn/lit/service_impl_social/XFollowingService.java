package de.tuberlin.tkn.lit.service_impl_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Following;
import de.tuberlin.tkn.lit.service_interface_social.IFollowingService;
import de.tuberlin.tkn.lit.storage_social.IFollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XFollowingService implements IFollowingService {

    @Override
    public IFollowingRepository getRepository() {
        return repository;
    }

    @Autowired
    private IFollowingRepository repository;

    @Override
    public List<Following> findAll() {

        return (List<Following>) repository.findAll();
    }
}
