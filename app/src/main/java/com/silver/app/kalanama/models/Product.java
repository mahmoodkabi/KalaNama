package com.silver.app.kalanama.models;

/**
 * Created by admin on 08/29/2015.
 */
public class Product {

    private int productId;
    private String title;
    private String description;
    private float score;
    private String descriptionURL;
    private int viewCount;
    private int discussionCount;
    private int faqCount;



    public Product() {

    }


    public int getProductId() {
        return productId;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getScore() {
        return score;
    }

    public String getDescriptionURL() {
        return descriptionURL;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getFAQCount() {
        return faqCount;
    }

    public int getDiscussionCount() {
        return discussionCount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScore(double score) {
        this.score = (float) score;
    }

    public void setDescriptionURL(String descriptionUrl) {
        this.descriptionURL = descriptionUrl;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setDiscussionCount(int discussionCount) {
        this.discussionCount = discussionCount;
    }

    public void setFAQCount(int faqCount) {
        this.faqCount = faqCount;
    }

}
