/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

/**
 *
 * @author algone
 */
@Entity
@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
public class Host implements Serializable {
    @Id
    private String id = new ObjectId().toHexString();
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String imageUrl;
    private boolean verified;
    private String aboutHost;
    private String whenJoined;
    private List<String> achievements = new ArrayList<>();
    private int hostRating;
    @Embedded
    private List<Review> hostReviews = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getAboutHost() {
        return aboutHost;
    }

    public void setAboutHost(String aboutHost) {
        this.aboutHost = aboutHost;
    }

    public String getWhenJoined() {
        return whenJoined;
    }

    public void setWhenJoined(String whenJoined) {
        this.whenJoined = whenJoined;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }

    public int getHostRating() {
        return hostRating;
    }

    public void setHostRating(int hostRating) {
        this.hostRating = hostRating;
    }

    public List<Review> getHostReviews() {
        return hostReviews;
    }

    public void setHostReviews(List<Review> hostReviews) {
        this.hostReviews = hostReviews;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
