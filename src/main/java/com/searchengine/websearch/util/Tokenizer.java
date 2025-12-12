package com.searchengine.websearch.util;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public static List<String> tokenize(String text) {

        List<String> tokens = new ArrayList<>();

        if (text == null || text.isEmpty()) return tokens;

        // 保留中英数字，其余替换为空格
        text = text.replaceAll("[^\\p{IsHan}a-zA-Z0-9]", " ");

        // 解决中英混合问题
        text = text.replaceAll("([a-zA-Z0-9])([\\p{IsHan}])", "$1 $2");
        text = text.replaceAll("([\\p{IsHan}])([a-zA-Z0-9])", "$1 $2");

        String[] parts = text.split("\\s+");

        for (String p : parts) {
            if (p.isEmpty()) continue;

            // 英文、数字整体保留
            if (p.matches("[a-zA-Z0-9]+")) {
                tokens.add(p.toLowerCase());
                continue;
            }

            // 中文逐字切分
            if (p.matches("[\\p{IsHan}]+")) {
                for (char c : p.toCharArray()) {
                    tokens.add(String.valueOf(c));
                }
            }
        }

        System.out.println("分词结果: " + tokens);
        return tokens;
    }
}