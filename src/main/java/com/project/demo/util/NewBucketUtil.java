package com.project.demo.util;

import com.project.demo.model.Article;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class NewBucketUtil {

    public static List<Article>parseArticles(Map<String,Object> response){
        List<Map<String,Object>>articlesData = (List<Map<String,Object>>) response.get("articles");

        return articlesData.stream().map(data-> new Article(
                (String) data.get("title"),
                (String) data.get("description"),
                (String) data.get("url"),
                (String) data.get("publishedAt")
        )).collect(Collectors.toList());
    }

    public static Map<Integer,List<Article>>groupArticleByInterval(List<Article>articles,
        String interval,int n){

        Map<Integer,List<Article>>groupedArticles = new TreeMap<>();
        Date now = new Date();

        articles.forEach(article -> {
            try {
                Date publishedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(article.getPublishedAt());
                long diff = now.getTime() - publishedDate.getTime();
                int bucket = calculateBucket(diff,interval);
                if(bucket <= n ){
                    groupedArticles.computeIfAbsent(bucket,k->new ArrayList<>())
                            .add(article);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return groupedArticles;
    }

    private static int calculateBucket(long diffMillis,String interval){
        switch (interval){
            case "minutes": return  (int) (diffMillis/(1000*60));
            case "hours": return  (int) (diffMillis/(1000*60*60));
            case "days": return  (int) (diffMillis/(1000*60*60*24));
            case "weeks": return  (int) (diffMillis/(1000*60*60*24*7));
            case "months": return  (int) (diffMillis/(1000*60*60*24*30));
            case "years": return  (int) (diffMillis/(1000*60*60*24*365));
            default: return 0;
        }
    }
}
