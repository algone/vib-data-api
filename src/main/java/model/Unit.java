/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author A4372949
 */
@Entity
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String unitHeading;
    private String unitDescription;
    private String postedBy;
    private int numOfBedrooms;
    private int numOfBathrooms;
    private int numOfBalconies;
    private int unitNumber; 
    private int unitFloorNumber;
    private boolean active;
    
    private RentalType rentalType = RentalType.SHORT_TERM;
    private UnitType unitType = UnitType.APARTMENT;
    private UnitPrivacy privacy = UnitPrivacy.ENTIRE_HOME;
    private FurnishingType furnishing = FurnishingType.UNFURNISHED;    


    private String dateOfPosting;

    private String dateAvailableFrom;
    
//   @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
   @Embedded
    private List<VibandaImage> unitImages=new ArrayList<>();
    @Embedded
    private UnitFeature unitFeature;
    @Embedded
    private RentalInfo rentalInfo;
    


    public Long getId() {
        return id;
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

}
