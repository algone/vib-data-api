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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author A4372949
 */
@Entity
@Table(name = "PARENT_UNIT")
public class ParentUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long parentId;
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parent")
    private List<VibandaImage> parentImages = new ArrayList<>();
    
//    @Embedded
//    private ParentUnitImage parentUnitImage = new ParentUnitImage();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_parentUnit")
    private List<Unit> rentalUnits = new ArrayList<>();

    public ParentUnit() {
    }

    public Long getParentId() {
        return parentId;
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

//    public ParentUnitImage getParentUnitImage() {
//        return parentUnitImage;
//    }
//
//    public void setParentUnitImage(ParentUnitImage parentUnitImage) {
//        this.parentUnitImage = parentUnitImage;
//    }
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
