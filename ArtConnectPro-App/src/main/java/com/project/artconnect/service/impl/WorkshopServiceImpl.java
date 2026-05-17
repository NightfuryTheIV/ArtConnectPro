package com.project.artconnect.service.impl;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.model.Booking;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.service.WorkshopService;
import java.util.List;
import java.util.Optional;

public class WorkshopServiceImpl implements WorkshopService {
    private final WorkshopDao workshopDao;

    public WorkshopServiceImpl(WorkshopDao workshopDao) {
        this.workshopDao = workshopDao;
    }

    @Override
    public List<Workshop> getAllWorkshops() {
        return workshopDao.findAll();
    }

    @Override
    public Optional<Workshop> getWorkshopByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public void bookWorkshop(Workshop workshop, CommunityMember member) {
    }

    @Override
    public List<Booking> getBookingsByMember(CommunityMember member) {
        return List.of();
    }
}