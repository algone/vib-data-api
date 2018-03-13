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
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.utils.IndexType;

/**
 *
 * @author A4372949
 */
@Entity
@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
public class Unit implements Serializable {

    @Id
    private String  id= new ObjectId().toHexString();
    private String unitHeading;
    private String unitDescription;
    private String postedBy;
    private int numOfBedrooms;
    private int numOfBathrooms;
    private int numOfBalconies;
    private int unitNumber;
    private int unitFloorNumber;
    private boolean active;
    private String ecorated;
    private String dateOfPosting;
    private String dateAvailableFrom;
    private String unitParentId;
    private Location location;
    private RentalType rentalType ;
    private UnitType unitType ;
    private UnitPrivacy privacy ;
    private FurnishingType furnishing ;
    private String checkinTime ;
    private String checkoutTime ;

    @Embedded
    private List<VibandaImage> unitImages = new ArrayList<>();
    @Embedded
    private UnitFeature unitFeature;
    @Embedded
    private RentalInfo rentalInfo;
    @Reference
    private List<Rating> unitRatings = new ArrayList<>();

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



    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
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

    public int getNumOfBalconies() {
        return numOfBalconies;
    }

    public void setNumOfBalconies(int numOfBalconies) {
        this.numOfBalconies = numOfBalconies;
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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
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

    public int getUnitFloorNumber() {
        return unitFloorNumber;
    }

    public void setUnitFloorNumber(int unitFloorNumber) {
        this.unitFloorNumber = unitFloorNumber;
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

    public UnitPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(UnitPrivacy privacy) {
        this.privacy = privacy;
    }

    public FurnishingType getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(FurnishingType furnishing) {
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

}
