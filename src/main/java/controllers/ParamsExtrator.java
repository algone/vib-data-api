/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Set;
import model.FurnishingType;
import model.Location;
import model.ParentUnit;
import model.ParentUnitAccessibility;
import model.RentalInfo;
import model.RentalType;
import model.Unit;
import model.UnitFeature;
import model.UnitPrivacy;
import model.UnitStyle;
import model.UnitType;
import ninja.Context;

/**
 *
 * @author algone
 */
public class ParamsExtrator {

    private Context context;
    private Set<String> keys;
    private ParentUnit vpu;
    private Unit vUnit = new Unit();

    public ParamsExtrator(Context context) {
        vpu = new ParentUnit();
        this.context = context;
        this.keys = context.getParameters().keySet();

    }

    private void extractParentAccessibility() {

        for (String key : keys) {

            /*
        Accessibility
             */
            if (key.matches("hasBanks")) {
                vpu.getParentUnitAccessibility().setHasBanks(context.getParameter(key).contentEquals("on"));
            }

            if (key.matches("hasAirport")) {
                vpu.getParentUnitAccessibility().setHasAirport(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("publicTransport")) {
                vpu.getParentUnitAccessibility().setPublicTransportStation(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasSchools")) {
                vpu.getParentUnitAccessibility().setHasSchools(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasATM")) {
                vpu.getParentUnitAccessibility().setHasATM(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasParks")) {
                vpu.getParentUnitAccessibility().setHasParks(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasGroceryStore")) {
                vpu.getParentUnitAccessibility().setHasGroceryStore(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasRestaurant")) {
                vpu.getParentUnitAccessibility().setHasRestaurant(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasMall")) {
                vpu.getParentUnitAccessibility().setHasMall(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasBakery")) {
                vpu.getParentUnitAccessibility().setHasBakery(context.getParameter(key).contentEquals("on"));
            }
        }
    }

    private void extractParentLocation() {
        /*
        Location Params
         */
        for (String key : keys) {

//            if (key.matches("city")) {
//                vpu.getLocation().setCity(context.getParameter(key));
//            }
            if (key.matches("county")) {
                vpu.getLocation().setCountyName(context.getParameter(key));
            }
            if (key.matches("address")) {
                vpu.getLocation().setAddress(context.getParameter(key));
            }
            if (key.matches("street")) {
                vpu.getLocation().setStreetName(context.getParameter(key));
            }
            if (key.matches("lat")) {
                vpu.getLocation().setLat(Double.parseDouble(context.getParameter(key)));
            }
            if (key.matches("lon")) {
                vpu.getLocation().setLon(Double.parseDouble(context.getParameter(key)));
            }
        }
    }

    private void extractParentAttributes() throws NumberFormatException {
        /*
        MAin Unit Attributes
         */
        for (String key : keys) {

            if (key.matches("unitName")) {
                vpu.setUnitName(context.getParameter(key));
            }
            if (key.matches("propertydescription")) {
                vpu.setPropertyDescription(context.getParameter(key));
            }
            if (key.matches("style")) {
                vpu.setStyle(UnitStyle.valueOf(context.getParameter(key)));
            }
            if (key.matches("ecorated")) {
                vpu.setEcorating(context.getParameter(key));
            }
            if (key.matches("parentType")) {
                vpu.setPropertyDescription(context.getParameter(key));
            }

        }
    }

    private void extractUnitProperties() throws NumberFormatException {

        for (String key : keys) {
            if (key.matches("unitParent")) {
                vUnit.setUnitParentId(context.getParameter(key));
            }
            if (key.matches("unitHeading")) {
                vUnit.setUnitHeading(context.getParameter(key));
            }
            if (key.matches("unitDescription")) {
                vUnit.setUnitDescription(context.getParameter(key));
            }
            if (key.matches("rentalType")) {
                vUnit.setRentalType(context.getParameter(key));
            }
            if (key.matches("unitType")) {
                vUnit.setUnitType(UnitType.valueOf(context.getParameter(key)));
            }
            if (key.matches("privacy")) {
                vUnit.setPrivacy(UnitPrivacy.valueOf(context.getParameter(key)));
            }
            if (key.matches("furnishingType")) {
                vUnit.setFurnishing(FurnishingType.valueOf(context.getParameter(key)));
            }
            if (key.matches("active")) {
                vUnit.setActive(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("numOfBedrooms")) {
                vUnit.setNumOfBedrooms(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("numOfBathrooms")) {
                vUnit.setNumOfBathrooms(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("beds")) {
                vUnit.setBeds(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("unitNumber")) {
                vUnit.setUnitNumber(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("postDate")) {
                vUnit.setDateOfPosting(context.getParameter(key));
            }
            if (key.matches("availableFrom")) {
                vUnit.setDateAvailableFrom(context.getParameter(key));
            }
            if (key.matches("checkinTime")) {
                vUnit.setCheckinTime(context.getParameter(key));
            }
            if (key.matches("checkoutime")) {
                vUnit.setCheckoutTime(context.getParameter(key));
            }
        }
    }

    private void extractAmmenities() {
        /*
        Amenities
         */
        for (String key : keys) {

            if (key.matches("hasCarpet")) {
                vUnit.getUnitFeature().setHasCarpet(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("petsAllowed")) {
                vUnit.getUnitFeature().setPetsAllowed(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasWalkinCloset")) {
                vUnit.getUnitFeature().setHasWalkinCloset(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasInUnitLandry")) {
                vUnit.getUnitFeature().setHasInUnitLandry(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasInUnitGarden")) {
                vUnit.getUnitFeature().setHasInUnitGarden(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasCeilingFanCooling")) {
                vUnit.getUnitFeature().setHasCeilingFanCooling(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasTV")) {
                vUnit.getUnitFeature().setHasTV(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasKitchen")) {
                vUnit.getUnitFeature().setHasKitchen(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasInUnitWifi")) {
                vUnit.getUnitFeature().setHasInUnitWifi(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasRefridgerator")) {
                vUnit.getUnitFeature().setHasRefridgerator(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasFirePlace")) {
                vUnit.getUnitFeature().setHasFirePlace(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasDishwasher")) {
                vUnit.getUnitFeature().setHasDishwasher(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("hasInUnitGym")) {
                vUnit.getUnitFeature().setHasInUnitGym(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("wifi")) {
                vUnit.getUnitFeature().setWifi(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("cable")) {
                vUnit.getUnitFeature().setCable(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("fitnessCentre")) {
                vUnit.getUnitFeature().setFitnessCentre(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("swimmingPool")) {
                vUnit.getUnitFeature().setSwimmingPool(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("laundry")) {
                vUnit.getUnitFeature().setLaundry(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("wheelchairAccessibility")) {
                vUnit.getUnitFeature().setWheelchairAccessibility(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("intercomFacilities")) {
                vUnit.getUnitFeature().setIntercomFacilities(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("powerBackup")) {
                vUnit.getUnitFeature().setPowerBackup(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("mainDoorSecurity")) {
                vUnit.getUnitFeature().setMainDoorSecurity(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("perimeterWall")) {
                vUnit.getUnitFeature().setPerimeterWall(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("lift")) {
                vUnit.getUnitFeature().setLift(context.getParameter(key).contentEquals("on"));
            }
        }
    }

    private void extractPriceInfo() throws NumberFormatException {
        /*
        Price info
         */
        for (String key : keys) {

            if (key.matches("currencyType")) {
                vUnit.getRentalInfo().setCurrencyType(context.getParameter(key));
            }

            if (key.matches("shortTermRentalPrice")) {
                vUnit.getRentalInfo().setShortTermRentalPrice(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("longTermRentalPrice")) {
                vUnit.getRentalInfo().setLongTermRentalPrice(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("serviceFee")) {
                vUnit.getRentalInfo().setServiceFee(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("securityDeposit")) {
                vUnit.getRentalInfo().setSecurityDeposit(Integer.parseInt(context.getParameter(key)));
            }

            if (key.matches("extraCharge")) {
                vUnit.getRentalInfo().setPerExtraGuestCharge(Double.parseDouble(context.getParameter(key)));
            }
            if (key.matches("includeMeals")) {
                vUnit.getRentalInfo().setIncludeMeals(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("childDiscount")) {
                vUnit.getRentalInfo().setChildDiscount(Double.parseDouble(context.getParameter(key)));
            }
            if (key.matches("cancellationPolicy")) {
                vUnit.getRentalInfo().setCancellationPolicy(context.getParameter(key));
            }
        }
    }

    public ParentUnit getParent() {
        vpu.setLocation(new Location());
        vpu.setParentUnitAccessibility(new ParentUnitAccessibility());
        vpu.setRentalUnits(new ArrayList<>());
        extractParentAttributes();
        extractParentLocation();
        extractParentAccessibility();
        return this.vpu;
    }

    public Unit getUnit() {
        vUnit.setUnitFeature(new UnitFeature());
        vUnit.setRentalInfo(new RentalInfo());
        extractUnitProperties();
        extractAmmenities();
        extractPriceInfo();
        return vUnit;
    }
}
