package com.searchengine.websearch.search;

import com.searchengine.websearch.db.DBUtil;
import com.searchengine.websearch.dao.DocumentDAO;
import com.searchengine.websearch.dao.DocumentDAO.Document;
import com.searchengine.websearch.util.Tokenizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SearchEngine {

    public List<Document> search(String keyword) {
        List<String> tokens = Tokenizer.tokenize(keyword);
        if (tokens.isEmpty()) return Collections.emptyList();

        // 结果文档 id -> 得分（暂时用词频）
        Map<Integer, Integer> scoreMap = new HashMap<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT doc_id, freq FROM inverted_index WHERE word = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (String token : tokens) {
                ps.setString(1, token);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int docId = rs.getInt("doc_id");
                    int freq = rs.getInt("freq");

                    scoreMap.put(docId, scoreMap.getOrDefault(docId, 0) + freq);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据得分排序（高频在前）
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(scoreMap.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        // 读取文档内容返回
        DocumentDAO dao = new DocumentDAO();
        List<Document> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            result.add(dao.getDocumentById(entry.getKey()));
        }

        return result;
    }
}