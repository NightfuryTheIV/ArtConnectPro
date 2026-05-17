package com.project.artconnect.dao.impl;

import com.project.artconnect.dao.ExhibitionDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDaoImpl implements ExhibitionDao {
    private Connection conn;

    public ExhibitionDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new ArrayList<>();
        String sql = "SELECT e.EXTitle, e.EXStartDate, e.EXTheme, e.EXEndDate, " +
                "e.EXDescription, e.EXCuratorName, " +
                "g.GName, g.GAddress, g.GRating " +
                "FROM Exhibitions e " +
                "LEFT JOIN Gallery g ON e.GalleryID = g.GalleryID";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Gallery gallery = new Gallery();
                gallery.setName(rs.getString("GName"));
                gallery.setAddress(rs.getString("GAddress"));
                gallery.setRating(rs.getDouble("GRating"));

                Exhibition exhibition = new Exhibition();
                exhibition.setTitle(rs.getString("EXTitle"));

                Date startDate = rs.getDate("EXStartDate");
                if (startDate != null) {
                    exhibition.setStartDate(startDate.toLocalDate());
                }

                Date endDate = rs.getDate("EXEndDate");
                if (endDate != null) {
                    exhibition.setEndDate(endDate.toLocalDate());
                }

                exhibition.setTheme(rs.getString("EXTheme"));
                exhibition.setDescription(rs.getString("EXDescription"));
                exhibition.setCuratorName(rs.getString("EXCuratorName"));
                exhibition.setGallery(gallery);

                exhibitions.add(exhibition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibitions;
    }

    @Override
    public void save(Exhibition exhibition) {
        String sql = "INSERT INTO Exhibitions (ExhibitionID, EXTitle, EXStartDate, EXTheme, EXEndDate, EXDescription, EXCuratorName, GalleryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, getNextExhibitionID());
            ps.setString(2, exhibition.getTitle());
            ps.setDate(3, exhibition.getStartDate() != null ? Date.valueOf(exhibition.getStartDate()) : null);
            ps.setString(4, exhibition.getTheme());
            ps.setDate(5, exhibition.getEndDate() != null ? Date.valueOf(exhibition.getEndDate()) : null);
            ps.setString(6, exhibition.getDescription());
            ps.setString(7, exhibition.getCuratorName());
            ps.setInt(8, exhibition.getGallery() != null ? getGalleryIDByName(exhibition.getGallery().getName()) : 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Exhibition exhibition) {
        String sql = "UPDATE Exhibitions SET EXStartDate = ?, EXTheme = ?, EXEndDate = ?, EXDescription = ?, EXCuratorName = ?, GalleryID = ? WHERE EXTitle = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, exhibition.getStartDate() != null ? Date.valueOf(exhibition.getStartDate()) : null);
            ps.setString(2, exhibition.getTheme());
            ps.setDate(3, exhibition.getEndDate() != null ? Date.valueOf(exhibition.getEndDate()) : null);
            ps.setString(4, exhibition.getDescription());
            ps.setString(5, exhibition.getCuratorName());
            ps.setInt(6, exhibition.getGallery() != null ? getGalleryIDByName(exhibition.getGallery().getName()) : 0);
            ps.setString(7, exhibition.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String title) {
        String sql = "DELETE FROM Exhibitions WHERE EXTitle = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getNextExhibitionID() throws SQLException {
        String sql = "SELECT MAX(ExhibitionID) FROM Exhibitions";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }
        return 1;
    }

    private int getGalleryIDByName(String galleryName) throws SQLException {
        String sql = "SELECT GalleryID FROM Gallery WHERE GName = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, galleryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("GalleryID");
                }
            }
        }
        throw new SQLException("Gallery not found: " + galleryName);
    }
}