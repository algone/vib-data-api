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

import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import model.Location;
import model.ParentUnit;
import model.ParentUnitAccessibility;
import model.ParentUnitFacilities;
import model.Unit;
import model.UnitStyle;
import ninja.Context;
import ninja.params.Param;
import ninja.uploads.FileItem;

@Singleton
public class ApplicationController {

    private String PARAMS = "city,county,description,units,fitnessCentre,bank,floors,filepath,school,laundry,street,busstop,intercomm,cable,park,powerBackup,wifi,address,unitName,swimmingPool,airport,parameterWall,wheelchairAccess,elevators,grocery";

    public Result index() {

        return Results.html();

    }

    public Result helloWorldJson() {

        ParentUnit pu = new ParentUnit();

        pu.getRentalUnits().add(new Unit());

        return Results.json().render(pu);

    }

    public Result addParentUnit(Context context) throws Exception {
        FileItem upfile = context.getParameterAsFileItem("puImage");
        Map<String, List<FileItem>> items = context.getParameterFileItems();
        items.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((fItems) -> {
            for (FileItem fItem : fItems) {
                System.out.println("File: " + fItem.getFileName());
            }
        });
        Map<String, String[]> params = context.getParameters();
        ParentUnit vpu = new ParentUnit();
        Location unitLoc = new Location();
        ParentUnitFacilities puf = new ParentUnitFacilities();
        ParentUnitAccessibility accessibility = new ParentUnitAccessibility();
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
                puf.setWifi(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("cable")) {
                puf.setCable(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("fitnessCenter")) {
                puf.setFitnessCentre(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("swimmingPool")) {
                puf.setSwimmingPool(context.getParameter(key).contentEquals("on"));
            }        
            if (key.matches("laundry")) {
                puf.setLaundry(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("wheelchairAccess")) {
                puf.setWheelchairAccessibility(context.getParameter(key).contentEquals("on"));
            }            
            if (key.matches("intercomFacilities")) {
                puf.setIntercomFacilities(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("powerBackup")) {
                puf.setPowerBackup(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("mainDoorSecurity")) {
                puf.setMainDoorSecurity(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("perimeterWall")) {
                puf.setPerimeterWall(context.getParameter(key).contentEquals("on"));
            }
            if (key.matches("lift")) {
                puf.setLift(context.getParameter(key).contentEquals("on"));
            }
            vpu.setParentUnitFacilities(puf);

            /*
            Location Params
             */
            if (key.matches("city")) {
                unitLoc.setCity(context.getParameter(key));
            }
            if (key.matches("county")) {
                unitLoc.setCountyName(context.getParameter(key));
            }
            if (key.matches("address")) {
                unitLoc.setAddress(context.getParameter(key));
            }
            if (key.matches("street")) {
                unitLoc.setStreetName(context.getParameter(key));
            }

            vpu.setLocation(unitLoc);

            /*
            Accessibility
             */
            if (key.matches("hasBanks")) {
                accessibility.setHasBanks(context.getParameter(key).contentEquals("on"));

            }

            if (key.matches("hasAirport")) {
                accessibility.setHasAirport(context.getParameter(key).contentEquals("on"));

            }
            if (key.matches("publicTransport")) {
                accessibility.setPublicTransportStation(context.getParameter(key).contentEquals("on"));

            }
            if (key.matches("hasSchools")) {
                accessibility.setHasSchools(context.getParameter(key).contentEquals("on"));

            }
            if (key.matches("hasATM")) {
                accessibility.setHasATM(context.getParameter(key).contentEquals("on"));

            }
            if (key.matches("hasParks")) {
                accessibility.setHasParks(context.getParameter(key).contentEquals("on"));

            }
            if (key.matches("hasGroceryStore")) {
                accessibility.setHasGroceryStore(context.getParameter(key).contentEquals("on"));

            }

            vpu.setParentUnitAccessibility(accessibility);

        }

        return Results.json().render(vpu);
    }
}
