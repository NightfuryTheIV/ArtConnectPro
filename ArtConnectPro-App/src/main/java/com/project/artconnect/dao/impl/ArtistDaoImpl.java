package com.project.artconnect.dao.impl;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDaoImpl implements ArtistDao {
    private Connection conn;

    public ArtistDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        // On utilise YEAR() pour extraire uniquement l'année sous forme d'entier
        String sql = "SELECT ArtistID, AName, ACity, AEmail, YEAR(ABirthyear) AS ABirthyear, ABio, APhone, AWebsite, ASocialMedia, isActive FROM Artists";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Artist artist = new Artist();

                artist.setName(rs.getString("AName"));
                artist.setCity(rs.getString("ACity"));
                artist.setContactEmail(rs.getString("AEmail"));
                artist.setBirthYear(rs.getInt("ABirthyear")); // Plus d'erreur ici !
                artist.setBio(rs.getString("ABio"));
                artist.setPhone(rs.getString("APhone"));
                artist.setWebsite(rs.getString("AWebsite"));
                artist.setSocialMedia(rs.getString("ASocialMedia"));
                artist.setActive(rs.getBoolean("isActive"));

                artists.add(artist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artists;
    }

    @Override
    public void save(Artist artist) {
        String sql = "INSERT INTO Artists " +
                "(ArtistID, AName, ACity, AEmail, ABirthyear, ABio, APhone, AWebsite, ASocialMedia, isActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, getNextArtistID());
            ps.setString(2, artist.getName());
            ps.setString(3, artist.getCity());
            ps.setString(4, artist.getContactEmail());
            // Note : attention lors de la sauvegarde, si la BDD attend une date complète,
            // il faudra envoyer une chaîne au format "AAAA-MM-JJ" (ex: artist.getBirthYear() + "-01-01")
            ps.setString(5, artist.getBirthYear() + "-01-01");
            ps.setString(6, artist.getBio());
            ps.setString(7, artist.getPhone());
            ps.setString(8, artist.getWebsite());
            ps.setString(9, artist.getSocialMedia());
            ps.setBoolean(10, artist.isActive());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Artist artist) {
        String sql = "UPDATE Artists SET " +
                "ACity = ?, AEmail = ?, ABirthyear = ?, ABio = ?, " +
                "APhone = ?, AWebsite = ?, ASocialMedia = ?, isActive = ? " +
                "WHERE AName = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, artist.getCity());
            ps.setString(2, artist.getContactEmail());
            ps.setString(3, artist.getBirthYear() + "-01-01");
            ps.setString(4, artist.getBio());
            ps.setString(5, artist.getPhone());
            ps.setString(6, artist.getWebsite());
            ps.setString(7, artist.getSocialMedia());
            ps.setBoolean(8, artist.isActive());
            ps.setString(9, artist.getName());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String artistName) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Artists WHERE AName = ?")) {

            ps.setString(1, artistName);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Artist> findByCity(String city) {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT ArtistID, AName, ACity, AEmail, YEAR(ABirthyear) AS ABirthyear, ABio, APhone, AWebsite, ASocialMedia, isActive FROM Artists WHERE ACity = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artist artist = new Artist();

                    artist.setName(rs.getString("AName"));
                    artist.setCity(rs.getString("ACity"));
                    artist.setContactEmail(rs.getString("AEmail"));
                    artist.setBirthYear(rs.getInt("ABirthyear"));
                    artist.setBio(rs.getString("ABio"));
                    artist.setPhone(rs.getString("APhone"));
                    artist.setWebsite(rs.getString("AWebsite"));
                    artist.setSocialMedia(rs.getString("ASocialMedia"));
                    artist.setActive(rs.getBoolean("isActive"));

                    artists.add(artist);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artists;
    }

    private int getNextArtistID() throws SQLException {
        String sql = "SELECT MAX(ArtistID) FROM Artists";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }

        return 1;
    }
}