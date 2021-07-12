package de.tuberlin.tkn.lit.service_impl_social;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.social.Liked;
import de.tuberlin.tkn.lit.service_interface_social.ILikedService;
import de.tuberlin.tkn.lit.repos_social.ILikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class XLikedService implements ILikedService {

    @Override
    public ILikedRepository getRepository() {
        return repository;
    }

    @Autowired
    private ILikedRepository repository;

    @Override
    public List<Liked> findAll() {

        return (List<Liked>) repository.findAll();
    }
}
