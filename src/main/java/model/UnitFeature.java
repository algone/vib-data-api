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
public class UnitFeature implements Serializable {

    private boolean hasAirConditioning;
    private int numOfParking;
    private boolean hasCarpet;
    private boolean hasFirePlace;
    private boolean petsAllowed;
    private boolean hasWalkinCloset;
    private boolean hasInUnitLandry;
    private boolean hasInUnitGarden;
    private boolean hasCeilingFanCooling;
    private FurnishingType furnishingType = FurnishingType.FURNISHED;
    private boolean hasTV;
    private boolean hasKitchen;
    private boolean hasInUnitWifi;
    private boolean hasRefridgerator;
    private boolean hasFireplace;
    private boolean hasDishwasher;
    private boolean hasInUnitGym;
    private boolean hasVerandah;
    private boolean hasEnsuiteBathroom;

    public boolean isHasAirConditioning() {
        return hasAirConditioning;
    }

    public void setHasAirConditioning(boolean hasAirConditioning) {
        this.hasAirConditioning = hasAirConditioning;
    }

    public int getNumOfParking() {
        return numOfParking;
    }

    public void setNumOfParking(int numOfParking) {
        this.numOfParking = numOfParking;
    }

    public boolean isHasCarpet() {
        return hasCarpet;
    }

    public void setHasCarpet(boolean hasCarpet) {
        this.hasCarpet = hasCarpet;
    }

    public boolean isHasFirePlace() {
        return hasFirePlace;
    }

    public void setHasFirePlace(boolean hasFirePlace) {
        this.hasFirePlace = hasFirePlace;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public boolean isHasWalkinCloset() {
        return hasWalkinCloset;
    }

    public void setHasWalkinCloset(boolean hasWalkinCloset) {
        this.hasWalkinCloset = hasWalkinCloset;
    }

    public boolean isHasInUnitLandry() {
        return hasInUnitLandry;
    }

    public void setHasInUnitLandry(boolean hasInUnitLandry) {
        this.hasInUnitLandry = hasInUnitLandry;
    }

    public boolean isHasInUnitGarden() {
        return hasInUnitGarden;
    }

    public void setHasInUnitGarden(boolean hasInUnitGarden) {
        this.hasInUnitGarden = hasInUnitGarden;
    }

    public boolean isHasCeilingFanCooling() {
        return hasCeilingFanCooling;
    }

    public void setHasCeilingFanCooling(boolean hasCeilingFanCooling) {
        this.hasCeilingFanCooling = hasCeilingFanCooling;
    }

    public FurnishingType getFurnishingType() {
        return furnishingType;
    }

    public void setFurnishingType(FurnishingType furnishingType) {
        this.furnishingType = furnishingType;
    }

    public boolean isHasTV() {
        return hasTV;
    }

    public void setHasTV(boolean hasTV) {
        this.hasTV = hasTV;
    }

    public boolean isHasKitchen() {
        return hasKitchen;
    }

    public void setHasKitchen(boolean hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    public boolean isHasInUnitWifi() {
        return hasInUnitWifi;
    }

    public void setHasInUnitWifi(boolean hasInUnitWifi) {
        this.hasInUnitWifi = hasInUnitWifi;
    }

    public boolean isHasRefridgerator() {
        return hasRefridgerator;
    }

    public void setHasRefridgerator(boolean hasRefridgerator) {
        this.hasRefridgerator = hasRefridgerator;
    }

    public boolean isHasFireplace() {
        return hasFireplace;
    }

    public void setHasFireplace(boolean hasFireplace) {
        this.hasFireplace = hasFireplace;
    }

    public boolean isHasDishwasher() {
        return hasDishwasher;
    }

    public void setHasDishwasher(boolean hasDishwasher) {
        this.hasDishwasher = hasDishwasher;
    }

    public boolean isHasInUnitGym() {
        return hasInUnitGym;
    }

    public void setHasInUnitGym(boolean hasInUnitGym) {
        this.hasInUnitGym = hasInUnitGym;
    }

    public boolean isHasVerandah() {
        return hasVerandah;
    }

    public void setHasVerandah(boolean hasVerandah) {
        this.hasVerandah = hasVerandah;
    }

    public boolean isHasEnsuiteBathroom() {
        return hasEnsuiteBathroom;
    }

    public void setHasEnsuiteBathroom(boolean hasEnsuiteBathroom) {
        this.hasEnsuiteBathroom = hasEnsuiteBathroom;
    }

}
