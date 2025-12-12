package com.searchengine.websearch.index;

import com.searchengine.websearch.db.DBUtil;
import com.searchengine.websearch.dao.DocumentDAO;
import com.searchengine.websearch.dao.DocumentDAO.Document;
import com.searchengine.websearch.util.Tokenizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    public void buildIndex() {
        DocumentDAO dao = new DocumentDAO();
        List<Document> docs = dao.getAllDocuments();

        try (Connection conn = DBUtil.getConnection()) {

            String sql = "REPLACE INTO inverted_index(word, doc_id, freq) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (Document doc : docs) {
                List<String> tokens = Tokenizer.tokenize(doc.content);

                // 每个词对应的词频统计
                Map<String, Integer> freqMap = new HashMap<>();
                for (String token : tokens) {
                    freqMap.put(token, freqMap.getOrDefault(token, 0) + 1);
                }

                // 写入数据库
                for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
                    ps.setString(1, entry.getKey());
                    ps.setInt(2, doc.id);
                    ps.setInt(3, entry.getValue());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}