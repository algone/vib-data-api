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
import model.ParentUnitFacilities;
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
    private ParentUnit vpu = new ParentUnit();
    private Unit vUnit = new Unit();

    public ParamsExtrator(Context context) {
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
        }
    }

    private void extractParentLocation() {
        /*
        Location Params
         */
        for (String key : keys) {

            if (key.matches("city")) {
                vpu.getLocation().setCity(context.getParameter(key));
            }
            if (key.matches("county")) {
                vpu.getLocation().setCountyName(context.getParameter(key));
            }
            if (key.matches("address")) {
                vpu.getLocation().setAddress(context.getParameter(key));
            }
            if (key.matches("street")) {
                vpu.getLocation().setStreetName(context.getParameter(key));
            }
        }
    }

    private void extractParentFacilities() {

        for (String key : keys) {

            /*
        Parent Unit Facilities
             */
            if (key.matches("wifi")) {
                vpu.getParentUnitFacilities().setWifi(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("cable")) {
                vpu.getParentUnitFacilities().setCable(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("fitnessCentre")) {
                vpu.getParentUnitFacilities().setFitnessCentre(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("swimmingPool")) {
                vpu.getParentUnitFacilities().setSwimmingPool(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("laundry")) {
                vpu.getParentUnitFacilities().setLaundry(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("wheelchairAccessibility")) {
                vpu.getParentUnitFacilities().setWheelchairAccessibility(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("intercomFacilities")) {
                vpu.getParentUnitFacilities().setIntercomFacilities(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("powerBackup")) {
                vpu.getParentUnitFacilities().setPowerBackup(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("mainDoorSecurity")) {
                vpu.getParentUnitFacilities().setMainDoorSecurity(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("perimeterWall")) {
                vpu.getParentUnitFacilities().setPerimeterWall(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("lift")) {
                vpu.getParentUnitFacilities().setLift(context.getParameter(key).contentEquals("on"));
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
            if (key.matches("description")) {
                vpu.setDescription(context.getParameter(key));
            }
            if (key.matches("style")) {
                vpu.setStyle(UnitStyle.valueOf(context.getParameter(key)));
            }
            if (key.matches("numOfUnits")) {
                vpu.setNumOfUnits(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("numOfFloors")) {
                vpu.setNumOfFloors(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("ecorated")) {
                vpu.setDescription(context.getParameter(key));
            }
                     if (key.matches("parentType")) {
                vpu.setDescription(context.getParameter(key));
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
            if (key.matches("postedBy")) {
                vUnit.setPostedBy(context.getParameter(key));
            }
            if (key.matches("unitDescription")) {
                vUnit.setUnitDescription(context.getParameter(key));
            }
            if (key.matches("rentalType")) {
                vUnit.setRentalType(RentalType.valueOf(context.getParameter(key)));
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
            if (key.matches("numOfBalconies")) {
                vUnit.setNumOfBalconies(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("unitNumber")) {
                vUnit.setNumOfBedrooms(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("unitFloorNumber")) {
                vUnit.setUnitFloorNumber(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("stuff")) {
                vUnit.setNumOfBedrooms(Integer.parseInt(context.getParameter(key)));
            }

            if (key.matches("postDate")) {

                vUnit.setDateOfPosting(context.getParameter(key));

            }

            if (key.matches("availableFrom")) {

                vUnit.setDateAvailableFrom(context.getParameter(key));

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
            if (key.matches("cancellationFee")) {
                vUnit.getRentalInfo().setCancellationFee(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("adminFee")) {
                vUnit.getRentalInfo().setAdminFee(Integer.parseInt(context.getParameter(key)));
            }
            if (key.matches("securityDeposit")) {
                vUnit.getRentalInfo().setSecurityDeposit(Integer.parseInt(context.getParameter(key)));
            }

            if (key.matches("subleasingAllowed")) {
                vUnit.getRentalInfo().setSubleasingAllowed(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("cancellationAllowed")) {
                vUnit.getRentalInfo().setCancellationAllowed(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("includeMeals")) {
                vUnit.getRentalInfo().setIncludeMeals(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("childDiscount")) {
                vUnit.getRentalInfo().setChildDiscount(context.getParameter(key).contentEquals("on"));
            }
        }
    }

    public ParentUnit getParent() {
        vpu.setLocation(new Location());
        vpu.setParentUnitAccessibility(new ParentUnitAccessibility());
        vpu.setParentUnitFacilities(new ParentUnitFacilities());
        vpu.setRentalUnits(new ArrayList<>());
        extractParentAttributes();
        extractParentFacilities();
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
