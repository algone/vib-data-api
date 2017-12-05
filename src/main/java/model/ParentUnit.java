/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author A4372949
 */
public class ParentUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String unitName = "A Vibanda rental Unit";
    private String description;
    private UnitStyle style = UnitStyle.FAMILY;
    private int numOfUnits = 0;    
    private int numOfFloors = 0;
    
    private ParentUnitFacilities parentUnitFacilities = new ParentUnitFacilities();
    private Location location = new Location();
    private ParentUnitAccessibility parentUnitAccessibility = new ParentUnitAccessibility();
    
    private ParentUnitImage parentUnitImage = new ParentUnitImage();

    private List<Unit> rentalUnits = new ArrayList<>();
    
    
    public ParentUnit() {
    }

    public Long getId() {
        return id;
    }

    public String getParentUnitName() {
        return unitName;
    }

    public void setParentUnitName(String parentUnitName) {
        this.unitName = parentUnitName;
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

    public ParentUnitImage getParentUnitImage() {
        return parentUnitImage;
    }

    public void setParentUnitImage(ParentUnitImage parentUnitImage) {
        this.parentUnitImage = parentUnitImage;
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


}
