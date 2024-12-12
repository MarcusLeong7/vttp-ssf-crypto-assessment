package vttp.ssf.day21crypto.service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vttp.ssf.day21crypto.model.Article;
import vttp.ssf.day21crypto.repository.NewsRepository;
import vttp.ssf.day21crypto.utils.DateConvertor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepo;

    @Value("${my.api.key}")
    private String API_KEY;

    public static final String CRYPTO_API_URL = "https://min-api.cryptocompare.com/data/v2/news/?";

    //https://min-api.cryptocompare.com/data/v2/news/?lang=EN
    // API KEY in URL -  &api_key={your_api_key} at the end of your request url
    public List<Article> getArticles(String lang) {
        //Construct the URL
        String uri = UriComponentsBuilder
                .fromUriString(CRYPTO_API_URL)
                .queryParam("lang",lang)
                .queryParam("api_key",API_KEY)
                .toUriString();
        // Create the GET Request
        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        // Use RestTemplate to send the request
        RestTemplate restTemplate = new RestTemplate();
        List<Article> articles = new ArrayList<>();

        try{
            // Fetch response
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);
            String payload = response.getBody();
            System.out.println(payload);
            // Parse JSON response from a JSON string to Json Object
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject jsonObj = reader.readObject();
            System.out.println("Processing JsonObj");

            // Extract data array
            JsonArray data = jsonObj.getJsonArray("Data");

            // Loop through data array to get each article JSON Object
            for (int i = 0; i < data.size(); i++) {
                JsonObject article = data.getJsonObject(i); // Each article

                // Extracting relevant data for an article
                Article articleObj = new Article();
                String id = article.getString("id");
                String publishedOnString = article.getJsonNumber("published_on").toString();
                Date publishedOn = DateConvertor.stringToDate(publishedOnString);
                String title = article.getString("title");
                String url = article.getString("url");
                String imageurl = article.getString("imageurl");
                String body = article.getString("body");
                String tags = article.getString("tags");
                String category = article.getString("categories");

                // Set each article with relevant data
                articleObj.setId(id);
                articleObj.setPublishedOn(publishedOn);
                articleObj.setTitle(title);
                articleObj.setUrl(url);
                articleObj.setImageurl(imageurl);
                articleObj.setBody(body);
                articleObj.setTags(tags);
                articleObj.setCategories(category);
                //Add to list
                articles.add(articleObj);
            }

        } catch (Exception ex) {
            System.err.printf("Error fetching data: %s\n", ex.getMessage());
        }
        return articles;
    }

    public void saveArticles(List<Article> articles){
        for (Article article : articles) {
            newsRepo.save(article);
        }
    }

    public JsonObject getArticleById(String id) {
        JsonObject jsonObject = null;
        try {
            Article article = newsRepo.getNewsArticlesById(id);
            jsonObject = Json.createObjectBuilder()
                    .add("id", article.getId())
                    .add("title", article.getTitle())
                    .add("body", article.getBody())
                    .add("published_on", DateConvertor.dateToLong(article.getPublishedOn()))
                    .add("url", article.getUrl())
                    .add("imageurl", article.getImageurl())
                    .add("tags", article.getTags())
                    .add("categories", article.getCategories())
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
