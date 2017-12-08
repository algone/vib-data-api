/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author A4372949
 */
@Entity
public class ParentUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String unitName="";
    private String description="";
    private UnitStyle style = UnitStyle.FAMILY;
    private int numOfUnits = 0;
    private int numOfFloors = 0;

    @Embedded
    private ParentUnitFacilities parentUnitFacilities= new ParentUnitFacilities();
    @Embedded
    private Location location;
    @Embedded
    private ParentUnitAccessibility parentUnitAccessibility = new ParentUnitAccessibility();

    @Embedded
    private ParentUnitImage parentUnitImage= new ParentUnitImage();


    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Unit> rentalUnits = new ArrayList<>();

    public ParentUnit() {
    }

    public Long getId() {
        return id;
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
