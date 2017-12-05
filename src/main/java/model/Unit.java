/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author A4372949
 */
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String unitHeading;
    private String unitDescription;
    private UnitType unitType = UnitType.APPARTMENT;
    private int numOfBedrooms;
    private int numOfBathrooms;
    private int numOfBalconies;
    private RentalInfo rentalInfo;
    private Date dateOfPosting;
    private Date dateAvailableFrom;
    private String postedBy;
    private boolean active;
    private int unitNumber;
    private int unitFloorNumber;
    private List<UnitImage> unitImages;
    private UnitFeature unitFeature;
    private UnitPrivacy privacy = UnitPrivacy.ENTIRE_HOME;
    private FurnishingType furnishing = FurnishingType.UNFURNISHED;

    public Long getId() {
        return id;
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

    public Date getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(Date dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
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

    public List<UnitImage> getUnitImages() {
        return unitImages;
    }

    public void setUnitImages(List<UnitImage> unitImages) {
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
