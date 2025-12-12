package com.searchengine.websearch.dao;

import com.searchengine.websearch.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    public static class Document {
        public int id;
        public String title;
        public String content;

        public Document(int id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }

    public List<Document> getAllDocuments() {
        List<Document> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, title, content FROM document";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Document(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Document getDocumentById(int id) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, title, content FROM document WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Document(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addDocument(String title, String content) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO document(title, content) VALUES(?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, content);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}