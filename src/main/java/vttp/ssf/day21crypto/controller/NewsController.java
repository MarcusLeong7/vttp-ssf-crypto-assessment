package vttp.ssf.day21crypto.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vttp.ssf.day21crypto.model.Article;
import vttp.ssf.day21crypto.service.NewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping()
public class NewsController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping()
    public String getNews(
            @RequestParam(defaultValue = "EN") String lang,
            Model model,
            HttpSession session) {
        // Fetch list of articles
        List<Article> articleList = newsSvc.getArticles(lang);

        // Store list of articles in the session
        session.setAttribute("selectedArticles", articleList);

        // Add data to model
        model.addAttribute("article", articleList);

        return "news";
    }

    //  Post /articles
    @PostMapping("/articles")
    public String saveArticles(
            @RequestParam(value = "selectedArticles",required = false) List<String> articleIds,
            HttpSession session, Model model) {

        System.out.println(articleIds);

        // Retrieve articles from the session
        // Compiler cannot verify at runtime that the list contains String elements
        // SuppressWarnings to suppress the warning
        @SuppressWarnings("unchecked")
        List<Article> selectedArticles = (List<Article>) session.getAttribute("selectedArticles");

        // Check if any articles are selected
        if (articleIds == null || articleIds.isEmpty()) {
            model.addAttribute("message", "Status:No articles selected. Please select articles and try again.");
            model.addAttribute("article", selectedArticles);
            return "news";
        }
        System.out.println(selectedArticles);
        //Filter and stream selected articles
        List<Article> selectedArticleSave = selectedArticles.stream()
                .filter(article -> articleIds.contains(article.getId()))
                .collect(Collectors.toList());
        System.out.println("selectedArticleSave: " + selectedArticleSave);

        //Save to repo
        newsSvc.saveArticles(selectedArticleSave);
        System.out.println("test2");
        // Add a success message
        model.addAttribute("message", "Status:Selected articles have been saved successfully!");
        model.addAttribute("article", selectedArticleSave);

        return "news";

    }

}
