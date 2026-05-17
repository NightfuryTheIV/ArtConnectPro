package com.project.artconnect.util;

import com.project.artconnect.dao.impl.*;
import com.project.artconnect.service.*;
import com.project.artconnect.service.ExhibitionService;
import com.project.artconnect.service.impl.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service Provider to manage singleton instances of services and handle their
 * initialization.
 */
public class ServiceProvider {
    private static ArtistService artistService;
    private static ArtworkService artworkService;
    private static GalleryService galleryService;
    private static WorkshopService workshopService;
    private static CommunityService communityService;
    private static ExhibitionService exhibitionService;

    static {
        try {
            // 1. Ouverture de la connexion unique à MySQL
            Connection conn = ConnectionManager.getConnection();

            // 2. Branchement de TOUS les vrais services connectés à la base de données
            artistService = new ArtistServiceImpl(new ArtistDaoImpl(conn));
            artworkService = new ArtworkServiceImpl(new ArtworkDaoImpl(conn));
            galleryService = new GalleryServiceImpl(new GalleryDaoImpl(conn));
            workshopService = new WorkshopServiceImpl(new WorkshopDaoImpl(conn));
            communityService = new CommunityServiceImpl(new CommunityMemberDaoImpl(conn));
            exhibitionService = new ExhibitionServiceImpl(new ExhibitionDaoImpl(conn));

        } catch (SQLException e) {
            System.err.println("FATAL ERROR: Impossible de se connecter à la base de données !");
            e.printStackTrace();
        }
    }

    public static ArtistService getArtistService() {
        return artistService;
    }

    public static ArtworkService getArtworkService() {
        return artworkService;
    }

    public static GalleryService getGalleryService() {
        return galleryService;
    }

    public static WorkshopService getWorkshopService() {
        return workshopService;
    }

    public static CommunityService getCommunityService() {
        return communityService;
    }

    public static ExhibitionService getExhibitionService() {
        return exhibitionService;
    }
}