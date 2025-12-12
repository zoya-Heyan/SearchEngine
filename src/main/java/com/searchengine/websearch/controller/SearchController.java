package com.searchengine.websearch.controller;

import com.searchengine.websearch.search.SearchEngine;
import com.searchengine.websearch.dao.DocumentDAO.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class SearchController {

    private final List<String> history = new LinkedList<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("history", history);
        return "search";
    }

    @GetMapping("/search")
    public String search(String keyword, Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            history.add(0, keyword);
            if (history.size() > 5) {
                history.remove(history.size() - 1);
            }
        }

        SearchEngine engine = new SearchEngine();
        List<Document> results = engine.search(keyword);

        model.addAttribute("keyword", keyword);
        model.addAttribute("results", results);
        model.addAttribute("history", history);

        return "result";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @PostMapping("/add")
    public String addDocument(@RequestParam String title,
                              @RequestParam String content) {
        // 插入数据库
        com.searchengine.websearch.dao.DocumentDAO dao = new com.searchengine.websearch.dao.DocumentDAO();
        dao.addDocument(title, content);

        return "redirect:/";
    }
}