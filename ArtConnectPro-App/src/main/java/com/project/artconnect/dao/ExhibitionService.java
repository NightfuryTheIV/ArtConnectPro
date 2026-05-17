package com.project.artconnect.service;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import java.util.List;
import java.util.Optional;

public interface ExhibitionService {
    List<Exhibition> getAllExhibitions();

    Optional<Exhibition> getExhibitionByTitle(String title);

    List<Exhibition> getExhibitionsByGallery(Gallery gallery);
}