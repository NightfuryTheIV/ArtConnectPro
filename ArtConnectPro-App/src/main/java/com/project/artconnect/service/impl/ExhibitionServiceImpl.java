package com.project.artconnect.service.impl;

import com.project.artconnect.dao.ExhibitionDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.service.ExhibitionService;
import java.util.List;
import java.util.Optional;

public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionDao exhibitionDao;

    public ExhibitionServiceImpl(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }

    @Override
    public List<Exhibition> getAllExhibitions() {
        return exhibitionDao.findAll();
    }

    @Override
    public Optional<Exhibition> getExhibitionByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<Exhibition> getExhibitionsByGallery(Gallery gallery) {
        return List.of();
    }
}