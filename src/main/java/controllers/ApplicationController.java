/**
 * Copyright (C) 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.FurnishingType;
import model.Location;
import model.ParentUnit;
import model.ParentUnitAccessibility;
import model.ParentUnitFacilities;
import model.RentalInfo;
import model.RentalType;
import model.Unit;
import model.UnitFeature;
import model.VibandaImage;
import model.UnitPrivacy;
import model.UnitStyle;
import model.UnitType;
import ninja.Context;
import ninja.ReverseRouter;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import org.apache.commons.io.FileUtils;

@FileProvider(DiskFileItemProvider.class)
@Singleton
public class ApplicationController {

    @Inject
    ReverseRouter reverseRouter;
    @Inject
    Provider<EntityManager> entitiyManagerProvider;

    List<Unit> units = new ArrayList<>();
    List<VibandaImage> unitImages = new ArrayList<>();

    public Result index() {

        return Results.html();

    }

    public Result addUnit(Context context) {
        Map<String, String[]> params = context.getParameters();
        Unit vUnit = new Unit();
        vUnit.setUnitFeature(new UnitFeature());
        vUnit.setRentalInfo(new RentalInfo());

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
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
            /*
            Amenities
             */
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

            /*
            Price info
             */
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
        vUnit.getUnitImages().addAll(unitImages);
        unitImages.clear();
        units.add(vUnit);
        
        return Results.noContent();
    }

    @Transactional
    public Result addParent(Context context,
            @Param("parentUnitImage") FileItem parentUnitImage,
            @Param("puImageDescription") String parentUnitImageDesc) throws Exception {
        VibandaImage image = saveImage(parentUnitImage, parentUnitImageDesc);
        Map<String, String[]> params = context.getParameters();
        ParentUnit vpu = new ParentUnit();
        vpu.setParentUnitImage(image);
        vpu.setLocation(new Location());
        vpu.setParentUnitAccessibility(new ParentUnitAccessibility());
        vpu.setParentUnitFacilities(new ParentUnitFacilities());
        vpu.setRentalUnits(new ArrayList<>());

        //Clear units
       
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();

            System.out.println(key + ": " + context.getParameter(key));

            /*
            MAin Unit Attributes
             */
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

            /*
            Location Params
             */
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
        /*
        Add Pricing info
         */
        vpu.getRentalUnits().addAll(units);
      
        EntityManager entityManager = entitiyManagerProvider.get();
        entityManager.persist(vpu);
         units.clear();
        return Results.html().template("views/ApplicationController/index.ftl.html");

    }

    @UnitOfWork
    public Result listAll(Context context) {

        EntityManager entityManager = entitiyManagerProvider.get();

        Query q = entityManager.createQuery("SELECT pu FROM ParentUnit pu");
        List<ParentUnit> vpus = (List<ParentUnit>) q.getResultList();

        return Results.json().render(vpus);

    }

    public Result uploadUnitImage(Context context,
            @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("imageDescription") String imageDescription) throws Exception {

        VibandaImage img = saveImage(unitImageFile, imageDescription);
        unitImages.add(img);
//        return Results.json().render(img);
        return Results.noContent();
    }

    @Transactional
    public Result add(Context context, ParentUnit parent) {
        EntityManager entityManager = entitiyManagerProvider.get();
//        entityManager.persist(parentUnit);
//        return Results.html().template("views/ApplicationController/index.ftl.html").render("vpus", allPUs);
        return Results.json().render(parent);
    }

    @UnitOfWork
    public ParentUnit findParentUnit(String unitName) {

        EntityManager entityManager = entitiyManagerProvider.get();
        Query q = entityManager.createQuery("SELECT pu.unitName FROM ParentUnit pu WHERE pu.unitName LIKE '%" + unitName + "%'");
        ParentUnit vpu = (ParentUnit) q.getSingleResult();

        return vpu;

    }

    private VibandaImage saveImage(FileItem imageFile, String imageDescription) throws IOException {
        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));
        File defDir = new File(System.getProperty("user.dir") + "/src/main/java/assets/img/images");
        File destFile = new File(defDir, imageFile.getFileName());
        FileUtils.copyFile(imageFile.getFile(), destFile);
        VibandaImage img = new VibandaImage();
        img.setImageUrl("assets/img/images/" + imageFile.getFileName());
        img.setImageDescription(imageDescription);
        return img;
    }

    public Result createParentUnit(Context context) {
        ParentUnit parentUnit = new ParentUnit();
        parentUnit.setLocation(new Location());
        parentUnit.setParentUnitAccessibility(new ParentUnitAccessibility());
        parentUnit.setParentUnitFacilities(new ParentUnitFacilities());
        parentUnit.setRentalUnits(new ArrayList<>());
        parentUnit.setParentUnitImage(new VibandaImage());

        return Results.html().template("views/ApplicationController/index.ftl.html").render("pu", parentUnit);
    }

}
