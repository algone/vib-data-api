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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Location;
import model.ParentUnit;
import model.ParentUnitAccessibility;
import model.ParentUnitFacilities;
import model.ParentUnitImage;
import model.Unit;
import model.UnitImage;
import model.UnitStyle;
import ninja.Context;
import ninja.ReverseRouter;
import ninja.jpa.UnitOfWork;
import ninja.uploads.FileItem;

@Singleton
public class ApplicationController {

    @Inject
    ReverseRouter reverseRouter;
    @Inject
    Provider<EntityManager> entitiyManagerProvider;

    public Result index() {

        return Results.html();

    }

    public Result helloWorldJson() {

        ParentUnit pu = new ParentUnit();

        pu.getRentalUnits().add(new Unit());

        return Results.json().render(pu);

    }

    @UnitOfWork
    public Result getParentUnits(Context context) {

        EntityManager entityManager = entitiyManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM ParentUnit x");
        List<ParentUnit> guestbookEntries = (List<ParentUnit>) q.getResultList();

        return Results.json().render(guestbookEntries);

    }

    public Result createParentUnit(Context context) {

//        EntityManager entityManager = entitiyManagerProvider.get();
//        UnitImage img = new UnitImage();
//        img.setDescription("Some nice image description");
//        img.setImageUrl("https://static.pexels.com/photos/279701/pexels-photo-279701.jpeg");
//        entityManager.persist(img);
        ParentUnit parentUnit = new ParentUnit();
        parentUnit.setLocation(new Location());
        parentUnit.setParentUnitAccessibility(new ParentUnitAccessibility());
        parentUnit.setParentUnitFacilities(new ParentUnitFacilities());
        parentUnit.setRentalUnits(new ArrayList<>());
        parentUnit.setParentUnitImage(new ParentUnitImage());

        return Results.html().template("views/ApplicationController/index.ftl.html").render("pu", parentUnit);
    }

    @Transactional
    public Result addImage(UnitImage image) {

        System.out.println("In postRoute");

        EntityManager entityManager = entitiyManagerProvider.get();
        UnitImage img = new UnitImage();
        img.setDescription("Some nice image description");
        img.setImageUrl("https://static.pexels.com/photos/279701/pexels-photo-279701.jpeg");
        entityManager.persist(img);
        return Results.json().render(img);
    }

    @Transactional
    public Result addParentUnit(Context context) throws Exception {
//        FileItem upfile = context.getParameterAsFileItem("puImage");
//        Map<String, List<FileItem>> items = context.getParameterFileItems();
//        items.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((fItems) -> {
//            for (FileItem fItem : fItems) {
//                System.out.println("File: " + fItem.getFileName());
//            }
//        });
        Map<String, String[]> params = context.getParameters();
        ParentUnit vpu = new ParentUnit();
        vpu.setLocation(new Location());
        vpu.setParentUnitAccessibility(new ParentUnitAccessibility());
        vpu.setParentUnitFacilities(new ParentUnitFacilities());
        vpu.setRentalUnits(new ArrayList<>());
        vpu.setParentUnitImage(new ParentUnitImage());

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
        EntityManager entityManager = entitiyManagerProvider.get();
        entityManager.persist(vpu);
        return Results.json().render(vpu);
//        return add(vpu);
    }

    @Transactional
    public Result add(Context context, ParentUnit parent) {
        EntityManager entityManager = entitiyManagerProvider.get();
//        entityManager.persist(parentUnit);
//        return Results.html().template("views/ApplicationController/index.ftl.html").render("vpus", allPUs);
        return Results.json().render(parent);
    }

    @UnitOfWork
    public List<ParentUnit> getAllParentUnits() {

        EntityManager entityManager = entitiyManagerProvider.get();

        Query q = entityManager.createQuery("SELECT pu FROM ParentUnit pu");
        List<ParentUnit> vpus = (List<ParentUnit>) q.getResultList();

        return vpus;

    }

    @UnitOfWork
    public ParentUnit findParentUnit(String unitName) {

        EntityManager entityManager = entitiyManagerProvider.get();
        Query q = entityManager.createQuery("SELECT pu.unitName FROM ParentUnit pu WHERE pu.unitName LIKE '%" + unitName + "%'");
        ParentUnit vpu = (ParentUnit) q.getSingleResult();

        return vpu;

    }
}
