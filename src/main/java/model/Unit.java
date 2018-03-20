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
 * @author A4372949
 */
@Entity
@Indexes(
        @Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
public class Unit implements Serializable {

    @Id
    private String id = new ObjectId().toHexString();
    private String unitHeading;
    private String unitDescription;
    private int numOfBedrooms;
    private int numOfBathrooms;
    private int beds;
    private int unitNumber;
    private boolean active;
    private String ecorated;
    private String dateOfPosting;
    private String dateAvailableFrom;
    private String checkinTime;
    private String checkoutTime;
    private String unitParentId;
    private String cancellationPolicy;
    private Location location;
    private String rentalType;
    private UnitType unitType;
    private String privacy;
    private String furnishing;
    private ParentType parentType;

    @Embedded
    private List<VibandaImage> unitImages = new ArrayList<>();
    @Embedded
    private UnitFeature unitFeature;
    @Embedded
    private RentalInfo rentalInfo;
    @Embedded
    private List<Rating> unitRatings = new ArrayList<>();
    @Embedded
    private List<Review> unitReviews = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitParentId() {
        return unitParentId;
    }

    public void setUnitParentId(String unitParentId) {
        this.unitParentId = unitParentId;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public String getUnitHeading() {
        return unitHeading;
    }

    public void setUnitHeading(String unitHeading) {
        this.unitHeading = unitHeading;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public int getNumOfBedrooms() {
        return numOfBedrooms;
    }

    public void setNumOfBedrooms(int numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }

    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public void setNumOfBathrooms(int numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public RentalInfo getRentalInfo() {
        return rentalInfo;
    }

    public void setRentalInfo(RentalInfo rentalInfo) {
        this.rentalInfo = rentalInfo;
    }

    public String getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(String dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public String getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public void setDateAvailableFrom(String dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public List<VibandaImage> getUnitImages() {
        return unitImages;
    }

    public void setUnitImages(List<VibandaImage> unitImages) {
        this.unitImages = unitImages;
    }

    public UnitFeature getUnitFeature() {
        return unitFeature;
    }

    public void setUnitFeature(UnitFeature unitFeature) {
        this.unitFeature = unitFeature;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public List<Rating> getUnitRatings() {
        return unitRatings;
    }

    public void setUnitRatings(List<Rating> unitRatings) {
        this.unitRatings = unitRatings;
    }

    public String getEcorated() {
        return ecorated;
    }

    public void setEcorated(String ecorated) {
        this.ecorated = ecorated;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public ParentType getParentType() {
        return parentType;
    }

    public void setParentType(ParentType parentType) {
        this.parentType = parentType;
    }

    public List<Review> getUnitReviews() {
        return unitReviews;
    }

    public void setUnitReviews(List<Review> unitReviews) {
        this.unitReviews = unitReviews;
    }

}
