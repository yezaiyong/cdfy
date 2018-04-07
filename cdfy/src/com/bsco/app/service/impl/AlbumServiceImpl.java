package com.bsco.app.service.impl;

import org.springframework.stereotype.Service;

import com.bsco.app.model.Album;
import com.bsco.app.service.AlbumService;
import com.bsco.framework.service.impl.EntityServiceImpl;

@Service
public class AlbumServiceImpl extends EntityServiceImpl<Album> implements AlbumService {

}
