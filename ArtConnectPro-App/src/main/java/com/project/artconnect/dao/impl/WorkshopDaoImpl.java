package com.project.artconnect.dao.impl;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.model.Workshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkshopDaoImpl implements WorkshopDao {
    private Connection conn;

    public WorkshopDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public Optional<Workshop> findById(Long id) {
        String sql = "SELECT * FROM Workshops WHERE WorkshopID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToWorkshop(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Workshop> findAll() {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT * FROM Workshops";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                workshops.add(mapResultSetToWorkshop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workshops;
    }

    private Workshop mapResultSetToWorkshop(ResultSet rs) throws SQLException {
        Workshop workshop = new Workshop();
        workshop.setTitle(rs.getString("WSTitle"));

        // Dans ton SQL, WSDate est au format DATE pur ('2024-05-10')
        // On récupère via un getDate() et on le convertit proprement au format attendu par ton modèle
        java.sql.Date sqlDate = rs.getDate("WSDate");
        if (sqlDate != null) {
            workshop.setDate(sqlDate.toLocalDate().atStartOfDay());
        }

        workshop.setPrice(rs.getDouble("WSPrice"));
        workshop.setLevel(rs.getString("WSLevel"));
        workshop.setDurationMinutes(rs.getInt("DurationMinutes"));
        workshop.setMaxParticipants(rs.getInt("MaxParticipants"));
        workshop.setLocation(rs.getString("WSLocation"));
        workshop.setDescription(rs.getString("WSDescription"));
        return workshop;
    }
}