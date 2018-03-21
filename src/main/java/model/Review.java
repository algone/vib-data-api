/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author algone
 */
@Entity
@Embedded
public class Review extends BaseEntity implements Serializable{
    private String reviewerName;
    private String reviewerAvatar;
    private String dateOfReviev;
    private String reviewTitle;
    private String rewiewText;
    private Rating rating;

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerAvatar() {
        return reviewerAvatar;
    }

    public void setReviewerAvatar(String reviewerAvatar) {
        this.reviewerAvatar = reviewerAvatar;
    }

    public String getDateOfReviev() {
        return dateOfReviev;
    }

    public void setDateOfReviev(String dateOfReviev) {
        this.dateOfReviev = dateOfReviev;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getRewiewText() {
        return rewiewText;
    }

    public void setRewiewText(String rewiewText) {
        this.rewiewText = rewiewText;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}
