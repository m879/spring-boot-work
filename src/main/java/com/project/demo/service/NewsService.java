package com.project.demo.service;

import com.project.demo.model.Article;
import com.project.demo.util.NewBucketUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    @Value("${newsapi.url}")
    private String newApiUrl;

    @Value("${newsapi.key}")
    private String apiKey;


    public Map<Integer, List<Article>> getNewsByKeyword(String keyword,String interval,int n){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?q=%s&apiKey=%s",newApiUrl,keyword,apiKey);
        var response = restTemplate.getForObject(url,Map.class);
        var articles = NewBucketUtil.parseArticles(response);
        return NewBucketUtil.groupArticleByInterval(articles,interval,n);
    }

}
