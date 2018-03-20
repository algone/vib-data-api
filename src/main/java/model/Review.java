/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author algone
 */
@Entity
public class Review extends BaseEntity{
    private String reviewerName;
    private String eviewerAvatar;
    private String dateOfReviev;
    private String reviewTitle;
    private String rewiewText;

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getEviewerAvatar() {
        return eviewerAvatar;
    }

    public void setEviewerAvatar(String eviewerAvatar) {
        this.eviewerAvatar = eviewerAvatar;
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

}
