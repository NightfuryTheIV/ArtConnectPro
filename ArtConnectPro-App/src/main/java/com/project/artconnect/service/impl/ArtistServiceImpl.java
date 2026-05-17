package com.project.artconnect.service.impl;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.service.ArtistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistServiceImpl implements ArtistService { // Ce texte sera souligné en rouge, c'est normal !

    private final ArtistDao artistDao;

    public ArtistServiceImpl(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public List<Artist> getAllArtists() {
        // C'EST ICI QUE LA MAGIE OPÈRE !
        return artistDao.findAll();
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return Optional.empty();
    }

    @Override
    public void createArtist(Artist artist) {

    }

    @Override
    public void updateArtist(Artist artist) {

    }

    @Override
    public void deleteArtist(String name) {

    }

    @Override
    public List<Discipline> getAllDisciplines() {
        return List.of();
    }

    @Override
    public List<Artist> searchArtists(String query, String disciplineName, String city) {
        return List.of();
    }

    // LAISSEZ LE RESTE VIDE POUR L'INSTANT
}