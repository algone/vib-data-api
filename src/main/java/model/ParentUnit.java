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
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author A4372949
 */
@Entity("parentunits")
public class ParentUnit implements Serializable {
    @Id
    private ObjectId parentId = new ObjectId();
    private String unitName = "";
    private String description = "";
    private UnitStyle style = UnitStyle.FAMILY;
    private int numOfUnits = 0;
    private int numOfFloors = 0;
    private boolean ecorated;

    @Embedded
    private ParentUnitFacilities parentUnitFacilities = new ParentUnitFacilities();
    @Embedded
    private Location location;
    @Embedded
    private ParentUnitAccessibility parentUnitAccessibility = new ParentUnitAccessibility();
    @Embedded
    private List<VibandaImage> parentImages;

    @Reference(idOnly=true)
    private List<Unit> rentalUnits;

    public ParentUnit() {
    }

    public ObjectId  getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
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

    public List<Unit> getRentalUnits() {
        return rentalUnits;
    }

    public void setRentalUnits(List<Unit> rentalUnits) {
        this.rentalUnits = rentalUnits;
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
