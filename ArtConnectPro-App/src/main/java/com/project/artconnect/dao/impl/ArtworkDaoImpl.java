package com.project.artconnect.dao.impl;

import com.project.artconnect.dao.ArtworkDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtworkDaoImpl implements ArtworkDao {
    private final Connection conn;

    public ArtworkDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public List<Artwork> findAll() {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT a.AWTitle, a.AWCreationYear, a.AWType, a.AWMedium, a.AWDimensions, " +
                "a.AWDescription, a.AWPrice, a.AWStatus, art.AName " +
                "FROM Artworks a " +
                "LEFT JOIN Artists art ON a.ArtistID = art.ArtistID";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Artwork artwork = new Artwork();
                artwork.setTitle(rs.getString("AWTitle"));
                artwork.setCreationYear(rs.getInt("AWCreationYear"));
                artwork.setType(rs.getString("AWType"));
                artwork.setMedium(rs.getString("AWMedium"));
                artwork.setDimensions(rs.getString("AWDimensions"));
                artwork.setDescription(rs.getString("AWDescription"));
                artwork.setPrice(rs.getDouble("AWPrice"));

                String dbStatus = rs.getString("AWStatus");
                if (dbStatus != null) {
                    String cleanStatus = dbStatus.trim();
                    if (cleanStatus.equalsIgnoreCase("Sold")) {
                        artwork.setStatus(Artwork.Status.SOLD);
                    } else if (cleanStatus.equalsIgnoreCase("Exhibited")) {
                        artwork.setStatus(Artwork.Status.EXHIBITED);
                    } else {
                        artwork.setStatus(Artwork.Status.FOR_SALE);
                    }
                } else {
                    artwork.setStatus(Artwork.Status.FOR_SALE);
                }

                String artistName = rs.getString("AName");
                if (artistName != null) {
                    Artist artist = new Artist();
                    artist.setName(artistName);
                    artwork.setArtist(artist);
                }

                artworks.add(artwork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    @Override
    public List<Artwork> findByArtistName(String artistName) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT a.AWTitle, a.AWCreationYear, a.AWType, a.AWMedium, a.AWDimensions, " +
                "a.AWDescription, a.AWPrice, a.AWStatus, art.AName " +
                "FROM Artworks a " +
                "INNER JOIN Artists art ON a.ArtistID = art.ArtistID " +
                "WHERE art.AName = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artistName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setTitle(rs.getString("AWTitle"));
                    artwork.setCreationYear(rs.getInt("AWCreationYear"));
                    artwork.setType(rs.getString("AWType"));
                    artwork.setMedium(rs.getString("AWMedium"));
                    artwork.setDimensions(rs.getString("AWDimensions"));
                    artwork.setDescription(rs.getString("AWDescription"));
                    artwork.setPrice(rs.getDouble("AWPrice"));

                    String dbStatus = rs.getString("AWStatus");
                    if (dbStatus != null) {
                        String cleanStatus = dbStatus.trim();
                        if (cleanStatus.equalsIgnoreCase("Sold")) {
                            artwork.setStatus(Artwork.Status.SOLD);
                        } else if (cleanStatus.equalsIgnoreCase("Exhibited")) {
                            artwork.setStatus(Artwork.Status.EXHIBITED);
                        } else {
                            artwork.setStatus(Artwork.Status.FOR_SALE);
                        }
                    } else {
                        artwork.setStatus(Artwork.Status.FOR_SALE);
                    }

                    Artist artist = new Artist();
                    artist.setName(rs.getString("AName"));
                    artwork.setArtist(artist);

                    artworks.add(artwork);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    @Override
    public void save(Artwork artwork) {}

    @Override
    public void update(Artwork artwork) {}

    @Override
    public void delete(String title) {}
}