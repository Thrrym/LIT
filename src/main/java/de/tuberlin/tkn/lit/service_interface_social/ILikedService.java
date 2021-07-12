package de.tuberlin.tkn.lit.service_interface_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Liked;
import de.tuberlin.tkn.lit.storage_social.ILikedRepository;

import java.util.List;

public interface ILikedService {

    List<Liked> findAll();

    ILikedRepository getRepository();
}
