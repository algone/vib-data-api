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
public class UnitFeature extends ParentUnitFacilities implements Serializable {

    private boolean ac;
    private boolean parking;
    private boolean carpet;
    private boolean fireplace;
    private boolean pets;
    private boolean wardrobe;
    private boolean garden;
    private boolean fan;
    private boolean tv;
    private boolean kitchen;
    private boolean refridgerator;
    private boolean dishwasher;
    private boolean gym;
    private boolean verandah;
    private boolean ensuite;
    private boolean breakfast;

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }


    public boolean isCarpet() {
        return carpet;
    }

    public void setCarpet(boolean carpet) {
        this.carpet = carpet;
    }

    public boolean isFireplace() {
        return fireplace;
    }

    public void setFireplace(boolean fireplace) {
        this.fireplace = fireplace;
    }

    public boolean isPets() {
        return pets;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public boolean isWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(boolean wardrobe) {
        this.wardrobe = wardrobe;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public boolean isFan() {
        return fan;
    }

    public void setFan(boolean fan) {
        this.fan = fan;
    }


    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isRefridgerator() {
        return refridgerator;
    }

    public void setRefridgerator(boolean refridgerator) {
        this.refridgerator = refridgerator;
    }
    public boolean isDishwasher() {
        return dishwasher;
    }

    public void setDishwasher(boolean dishwasher) {
        this.dishwasher = dishwasher;
    }

    public boolean isGym() {
        return gym;
    }

    public void setGym(boolean gym) {
        this.gym = gym;
    }

    public boolean isVerandah() {
        return verandah;
    }

    public void setVerandah(boolean verandah) {
        this.verandah = verandah;
    }

    public boolean isEnsuite() {
        return ensuite;
    }

    public void setEnsuite(boolean ensuite) {
        this.ensuite = ensuite;
    }

    public boolean isBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

}
