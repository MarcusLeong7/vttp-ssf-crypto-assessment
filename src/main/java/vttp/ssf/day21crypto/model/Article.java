package vttp.ssf.day21crypto.model;

import java.util.Date;

public class Article {

    private String id;
    private Date publishedOn;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "id='" + id + '\'' +
                ", publishedOn='" + publishedOn + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }
}
