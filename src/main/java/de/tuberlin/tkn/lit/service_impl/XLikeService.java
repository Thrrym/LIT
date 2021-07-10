package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.activities.Like;
import de.tuberlin.tkn.lit.model.lit.Paper;
import de.tuberlin.tkn.lit.service.ILikeService;
import de.tuberlin.tkn.lit.storage.ILikeRepository;
import de.tuberlin.tkn.lit.storage.IPaperRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
