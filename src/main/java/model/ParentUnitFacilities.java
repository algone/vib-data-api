/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


/**
 *
 * @author A4372949
 */
@Embeddable @Access(AccessType.FIELD)
public class ParentUnitFacilities implements Serializable {

    private boolean wifi ;
    private boolean cable ;
    private boolean fitnessCentre ;
    private boolean swimmingPool ;
    private boolean laundry ;
    private boolean wheelchairAccessibility ;
    private boolean intercomFacilities ;
    private boolean powerBackup ;
    private boolean mainDoorSecurity;
    private boolean perimeterWall ;
    private boolean lift ;

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isCable() {
        return cable;
    }

    public void setCable(boolean cable) {
        this.cable = cable;
    }

    public boolean isFitnessCentre() {
        return fitnessCentre;
    }

    public void setFitnessCentre(boolean fitnessCentre) {
        this.fitnessCentre = fitnessCentre;
    }

    public boolean isSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }

    public boolean isLaundry() {
        return laundry;
    }

    public void setLaundry(boolean laundry) {
        this.laundry = laundry;
    }

    public boolean isWheelchairAccessibility() {
        return wheelchairAccessibility;
    }

    public void setWheelchairAccessibility(boolean wheelchairAccessibility) {
        this.wheelchairAccessibility = wheelchairAccessibility;
    }

    public boolean isIntercomFacilities() {
        return intercomFacilities;
    }

    public void setIntercomFacilities(boolean intercomFacilities) {
        this.intercomFacilities = intercomFacilities;
    }

    public boolean isPowerBackup() {
        return powerBackup;
    }

    public void setPowerBackup(boolean powerBackup) {
        this.powerBackup = powerBackup;
    }

    public boolean isMainDoorSecurity() {
        return mainDoorSecurity;
    }

    public void setMainDoorSecurity(boolean mainDoorSecurity) {
        this.mainDoorSecurity = mainDoorSecurity;
    }

    public boolean isPerimeterWall() {
        return perimeterWall;
    }

    public void setPerimeterWall(boolean perimeterWall) {
        this.perimeterWall = perimeterWall;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }
    
    
}
