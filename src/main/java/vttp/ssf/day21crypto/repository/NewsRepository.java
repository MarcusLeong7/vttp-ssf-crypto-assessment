package vttp.ssf.day21crypto.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.ssf.day21crypto.model.Article;

import java.util.List;

@Repository
public class NewsRepository {

    @Autowired
    @Qualifier("redis-object")
    private RedisTemplate<String, Object> template;

    public void save(Article article) {
        template.opsForHash().put("Articles", article.getId(), article);
        System.out.println("Saving article with ID: " + article.getId());
    }

    public Article getNewsArticlesById(String id){
        return (Article) template.opsForHash().get("Articles",id);
    }
}
