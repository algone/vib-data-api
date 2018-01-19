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
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author A4372949
 */
@Entity(noClassnameStored = true)
public class ParentUnit implements Serializable {

    @Id
    private String id = new ObjectId().toHexString();
    private String unitName ;
    private String description ;
    private UnitStyle style ;
    private ParentType parentType;
    private int numOfUnits;
    private int numOfFloors;
    private boolean ecorated;

    @Embedded
    private ParentUnitFacilities parentUnitFacilities = new ParentUnitFacilities();
    @Embedded
    private Location location;
    @Embedded
    private ParentUnitAccessibility parentUnitAccessibility = new ParentUnitAccessibility();
    @Embedded
    private List<VibandaImage> parentImages;
    @Embedded
    private List<String> rentalUnits = new ArrayList<>();

    public ParentUnit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumOfFloors() {
        return numOfFloors;
    }

    public void setNumOfFloors(int numOfFloors) {
        this.numOfFloors = numOfFloors;
    }

    public int getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(int numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<VibandaImage> getParentImages() {
        return parentImages;
    }

    public void setParentImages(List<VibandaImage> parentImages) {
        this.parentImages = parentImages;
    }

    public ParentUnitAccessibility getParentUnitAccessibility() {
        return parentUnitAccessibility;
    }

    public void setParentUnitAccessibility(ParentUnitAccessibility parentUnitAccessibility) {
        this.parentUnitAccessibility = parentUnitAccessibility;
    }

    public List<String> getRentalUnits() {
        return rentalUnits;
    }

    public void setRentalUnits(List<String> rentalUnits) {
        this.rentalUnits = rentalUnits;
    }

    public ParentType getParentType() {
        return parentType;
    }

    public void setParentType(ParentType parentType) {
        this.parentType = parentType;
    }

    
    
    public UnitStyle getStyle() {
        return style;
    }

    public void setStyle(UnitStyle style) {
        this.style = style;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public ParentUnitFacilities getParentUnitFacilities() {
        return parentUnitFacilities;
    }

    public void setParentUnitFacilities(ParentUnitFacilities parentUnitFacilities) {
        this.parentUnitFacilities = parentUnitFacilities;
    }

    public boolean isEcorated() {
        return ecorated;
    }

    public void setEcorated(boolean ecorated) {
        this.ecorated = ecorated;
    }

}
