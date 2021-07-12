package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Like;
import de.tuberlin.tkn.lit.service_interface_activities.ILikeService;
import de.tuberlin.tkn.lit.storage_activities.ILikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XLikeService implements ILikeService {

    @Override
    public ILikeRepository getRepository() {
        return repository;
    }

    @Autowired
    private ILikeRepository repository;

    @Override
    public List<Like> findAll() {

        return (List<Like>) repository.findAll();
    }
}
