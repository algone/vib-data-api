/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author A4372949
 */
@Embedded
public class ParentUnitFacilities implements Serializable {

    private boolean wifi ;
    private boolean cable ;
    private boolean fitnesscentre ;
    private boolean pool ;
    private boolean laundry ;
    private boolean accessibility ;
    private boolean intercom ;
    private boolean powerbackup ;
    private boolean maindoorsecurity;
    private boolean gated ;
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

    public boolean isFitnesscentre() {
        return fitnesscentre;
    }

    public void setFitnesscentre(boolean fitnesscentre) {
        this.fitnesscentre = fitnesscentre;
    }

    public boolean isPool() {
        return pool;
    }

    public void setPool(boolean pool) {
        this.pool = pool;
    }

    public boolean isLaundry() {
        return laundry;
    }

    public void setLaundry(boolean laundry) {
        this.laundry = laundry;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    public boolean isIntercom() {
        return intercom;
    }

    public void setIntercom(boolean intercom) {
        this.intercom = intercom;
    }

    public boolean isPowerbackup() {
        return powerbackup;
    }

    public void setPowerbackup(boolean powerbackup) {
        this.powerbackup = powerbackup;
    }

    public boolean isMaindoorsecurity() {
        return maindoorsecurity;
    }

    public void setMaindoorsecurity(boolean maindoorsecurity) {
        this.maindoorsecurity = maindoorsecurity;
    }

    public boolean isGated() {
        return gated;
    }

    public void setGated(boolean gated) {
        this.gated = gated;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }
    
    
}
