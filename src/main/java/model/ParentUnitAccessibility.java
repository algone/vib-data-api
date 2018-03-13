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
public class ParentUnitAccessibility implements Serializable {

    private boolean hasSchools;
    private boolean hasParks;
    private boolean hasBanks;
    private boolean hasGroceryStore;
    private boolean hasATM;
    private boolean publicTransportStation;
    private boolean hasAirport;
    private boolean hasMall;
    private boolean hasRestaurant;
    private boolean hasBakery;

    public boolean isHasSchools() {
        return hasSchools;
    }

    public void setHasSchools(boolean hasSchools) {
        this.hasSchools = hasSchools;
    }

    public boolean isHasParks() {
        return hasParks;
    }

    public void setHasParks(boolean hasParks) {
        this.hasParks = hasParks;
    }

    public boolean isHasBanks() {
        return hasBanks;
    }

    public void setHasBanks(boolean hasBanks) {
        this.hasBanks = hasBanks;
    }

    public boolean isHasGroceryStore() {
        return hasGroceryStore;
    }

    public void setHasGroceryStore(boolean hasGroceryStore) {
        this.hasGroceryStore = hasGroceryStore;
    }

    public boolean isHasATM() {
        return hasATM;
    }

    public void setHasATM(boolean hasATM) {
        this.hasATM = hasATM;
    }

    public boolean isPublicTransportStation() {
        return publicTransportStation;
    }

    public void setPublicTransportStation(boolean publicTransportStation) {
        this.publicTransportStation = publicTransportStation;
    }

    public boolean isHasAirport() {
        return hasAirport;
    }

    public void setHasAirport(boolean hasAirport) {
        this.hasAirport = hasAirport;
    }

    public boolean isHasMall() {
        return hasMall;
    }

    public void setHasMall(boolean hasMall) {
        this.hasMall = hasMall;
    }

    public boolean isHasRestaurant() {
        return hasRestaurant;
    }

    public void setHasRestaurant(boolean hasRestaurant) {
        this.hasRestaurant = hasRestaurant;
    }

    public boolean isHasBakery() {
        return hasBakery;
    }

    public void setHasBakery(boolean hasBakery) {
        this.hasBakery = hasBakery;
    }

}
