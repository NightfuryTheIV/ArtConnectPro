package com.project.artconnect.dao.impl;

import com.project.artconnect.dao.GalleryDao;
import com.project.artconnect.model.Gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GalleryDaoImpl implements GalleryDao {
    private Connection conn;

    public GalleryDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public Optional<Gallery> findById(Long id) {
        Optional<Gallery> galleryOptional = Optional.empty();
        String sql = "SELECT * FROM Gallery WHERE GalleryID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Gallery gallery = new Gallery();
                    gallery.setName(rs.getString("GName"));
                    gallery.setAddress(rs.getString("GAddress"));
                    gallery.setRating(rs.getDouble("GRating"));
                    gallery.setOwnerName(rs.getString("GOwnerName"));
                    gallery.setOpeningHours(rs.getString("GOpeningHours"));
                    gallery.setContactPhone(rs.getString("GContactPhone"));
                    gallery.setWebsite(rs.getString("GWebsite"));
                    galleryOptional = Optional.of(gallery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return galleryOptional;
    }

    @Override
    public List<Gallery> findAll() {
        List<Gallery> galleries = new ArrayList<>();
        String sql = "SELECT * FROM Gallery";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Gallery gallery = new Gallery();
                gallery.setName(rs.getString("GName"));
                gallery.setAddress(rs.getString("GAddress"));
                gallery.setRating(rs.getDouble("GRating"));
                gallery.setOwnerName(rs.getString("GOwnerName"));
                gallery.setOpeningHours(rs.getString("GOpeningHours"));
                gallery.setContactPhone(rs.getString("GContactPhone"));
                gallery.setWebsite(rs.getString("GWebsite"));
                galleries.add(gallery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return galleries;
    }
}