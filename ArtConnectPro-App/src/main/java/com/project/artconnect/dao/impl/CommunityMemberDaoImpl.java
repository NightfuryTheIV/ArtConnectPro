package com.project.artconnect.dao.impl;

import com.project.artconnect.dao.CommunityMemberDao;
import com.project.artconnect.model.CommunityMember;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunityMemberDaoImpl implements CommunityMemberDao {
    private Connection conn;

    public CommunityMemberDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public Optional<CommunityMember> findById(Long id) {
        Optional<CommunityMember> communityMember = Optional.empty();
        String sql = "SELECT CMName, CMEmail, CMCity, YEAR(CMBirthyear) AS CMBirthyear, CMPhone, MembershipType FROM CommunityMember WHERE MemberID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CommunityMember cmm = new CommunityMember();
                    cmm.setName(rs.getString("CMName"));
                    cmm.setEmail(rs.getString("CMEmail"));
                    cmm.setCity(rs.getString("CMCity"));
                    cmm.setBirthYear(rs.getInt("CMBirthyear")); // Protégé par YEAR()
                    cmm.setPhone(rs.getString("CMPhone"));
                    cmm.setMembershipType(rs.getString("MembershipType"));
                    communityMember = Optional.of(cmm);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommunityMemberDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return communityMember;
    }

    @Override
    public List<CommunityMember> findAll() {
        List<CommunityMember> members = new ArrayList<>();
        String sql = "SELECT CMName, CMEmail, CMCity, YEAR(CMBirthyear) AS CMBirthyear, CMPhone, MembershipType FROM CommunityMember";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CommunityMember member = new CommunityMember();
                member.setName(rs.getString("CMName"));
                member.setEmail(rs.getString("CMEmail"));
                member.setCity(rs.getString("CMCity"));
                member.setBirthYear(rs.getInt("CMBirthyear")); // Protégé par YEAR()
                member.setPhone(rs.getString("CMPhone"));
                member.setMembershipType(rs.getString("MembershipType"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
}