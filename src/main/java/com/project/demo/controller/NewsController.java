package com.project.demo.controller;

import com.project.demo.model.Article;
import com.project.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/search")
    public Map<Integer, List<Article>> searchNews(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "hours") String interval,
            @RequestParam(defaultValue = "12") int n
    ){
        return newsService.getNewsByKeyword(keyword,interval,n);
    }

}
