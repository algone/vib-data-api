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
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Host;
import model.Location;
import model.ParentUnit;
import model.ParentUnitAccessibility;
import model.Unit;
import model.VibandaImage;

import ninja.Context;
import ninja.FilterWith;
import ninja.ReverseRouter;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import services.DataService;
import services.VibandaImageService;

@FileProvider(DiskFileItemProvider.class)
@Singleton
public class ApplicationController {

    @Inject
    DataService dbService;
    @Inject
    VibandaImageService imgService;
    @Inject
    ReverseRouter reverseRouter;
    List<Unit> units = new ArrayList<>();
    List<VibandaImage> unitImages = new ArrayList<>();

    @FilterWith(SecureFilter.class)
    public Result index() {
        List<ParentUnit> vpus = dbService.getAllParents();
        return Results.html().template("views/ApplicationController/index.ftl.html").render("parents", vpus);
    }

    public Result showUpload() {
        return Results.html().template("views/ApplicationController/index_wiz.ftl.html");
    }

    public Result showParentUnitForm() {
        List<Document> counties = dbService.getCounties();

        for (Document county : counties) {
            if (county.get("ke_counties") != null) {
                List<Document> countyDocs = (List<Document>) county.get("ke_counties");

                return Results.html().template("views/ApplicationController/parentUnitUpload.ftl.html").render("counties", countyDocs);
            }

        }
        return Results.html().template("views/ApplicationController/parentUnitUpload.ftl.html");
    }

    public Result showLoginForm() {
//        Map<String, Object> parents = dbService.getParentIds();
//        Map<String, Object> data = new HashMap<>();
//        data.put("parentUnits", parents);
//        data.put("msg", "");
        return Results.html().template("views/layout/login.ftl.html");
    }

    public Result showRegisterForm() {
//        Map<String, Object> parents = dbService.getParentIds();
//        Map<String, Object> data = new HashMap<>();
//        data.put("parentUnits", parents);
//        data.put("msg", "");
        return Results.html().template("views/layout/register.ftl.html");
    }

    public Result showUnitForm() {
        Map<String, Object> parents = dbService.getParentIds();
        Map<String, Object> data = new HashMap<>();
        data.put("parentUnits", parents);
        data.put("msg", "");
        return Results.html().template("views/ApplicationController/unitUpload.ftl.html").render("data", data);
    }

    public Result showImageUploadForm() {
        List<Unit> unitIds = dbService.getAllUnits();
        System.out.println("Showing image upload form");
//        Map<String, Object> data = new HashMap<>();
//        data.put("units", unitIds);
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("units", unitIds);

    }

    public Result addUnit(Context context) {
        ParamsExtrator pe = new ParamsExtrator(context);
        Unit vUnit = pe.getUnit();
        vUnit.getUnitImages().addAll(unitImages);
        unitImages.clear();
        units.add(vUnit);
        return Results.json().render(units);
    }

    @Transactional
    public Result addParent(Context context,
            @Param("parentUnitImage") FileItem parentUnitImage,
            @Param("puImageDescription") String parentUnitImageDesc) throws Exception {
        VibandaImage image = saveParentImage(parentUnitImage, parentUnitImageDesc);
        ParentUnit vpu = new ParamsExtrator(context).getParent();
        vpu.getParentImages().add(image);
        units.clear();

//        return Results.html().template("views/ApplicationController/index.ftl.html");
        dbService.addParent(vpu);
        return Results.json().render(vpu);

    }

    @UnitOfWork
    public Result listAll(Context context) {
        List<ParentUnit> vpus = dbService.getAllParents();
//        Map<String, Object> ids = dbService.getParentIds();
//        System.out.println("JSON:" + Results.json().render(vpus));
        return Results.json().render(vpus);

    }

    public Result uploadUnitImage(Context context,
            @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("imageDescription") String imageDescription) throws Exception {

        VibandaImage img = saveUnitImage(unitImageFile, imageDescription);
        unitImages.add(img);
//        return Results.json().render(img);
        return Results.noContent();
    }

    @UnitOfWork
    public Result findAllUnits() {

        List<Unit> vunits = dbService.getAllUnits();
        System.out.println("JSON:" + vunits);
        return Results.json().render(vunits);

    }

    private VibandaImage saveParentImage(FileItem imageFile, String imageDescription) throws IOException {
        File defDir = new File(System.getProperty("user.dir") + "/src/main/java/assets/img/images");
        File destFile = new File(defDir, imageFile.getFileName());
        FileUtils.copyFile(imageFile.getFile(), destFile);
        VibandaImage img = new VibandaImage();
        img.setImageUrl("assets/img/images/" + imageFile.getFileName());
        img.setImageDescription(imageDescription);
        return img;
    }

    private VibandaImage saveUnitImage(FileItem imageFile, String imageDescription) throws IOException {
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
        parentUnit.setRentalUnits(new ArrayList<>());

        VibandaImage img1 = new VibandaImage();

        img1.setImageDescription("Image1 descr");
        img1.setImageUrl("some/image/url/img.jpg");
        img1.setImageId("img1");
        parentUnit.getParentImages().add(img1);
        dbService.addParent(parentUnit);
        return Results.json().render(img1);
//        return Results.html().template("views/ApplicationController/index.ftl.html").render("pu", parentUnit);
    }
//

    @UnitOfWork
    public Result login(@Param("email") String email, @Param("password") String pass, Context context) {

        Host user = dbService.userExists(email);

        if (user != null) {

            //User exists ...go to demo page
            if (context.getSession().get("userId") != null) {
                //additionally check if userId matches the provided email
                return Results.html().template("views/ApplicationController/index.ftl.html").render("msg", user.getUserName());
            }
            context.getSession().put("userId", user.getEmail());

            return Results.html().template("/views/ApplicationController/index.ftl.html").render("msg", "Welcome " + user.getUserName());
        } else {
            //User does not exists ... go to login page
            context.getSession().clear();
            return Results.html().template("/views/layout/register.ftl.html").render("msg", "Login");
        }

    }

    @Transactional
    public Result register(@Param("userName") String userName,
            @Param("email") String email,
            @Param("password") String pass,
            @Param("firstName") String fname,
            @Param("lastName") String lname,
            Context context) {
        Host user = dbService.userExists(email);

        if (user != null) {
            //User exists ...go to login page and log in

            return Results.html().template("/views/layout/signin.ftl.html").render("msg", "User " + userName + " already exists, got to login");
        } else {
            //User does not exists ... go to login page
            Host host = new Host();
            host.setUserName(userName);
            host.setEmail(email);
            host.setPassword(pass);
            host.setFirstName(fname);
            host.setLastName(lname);
host.setHostReviews(new ArrayList<>());
            dbService.addHost(host);
            return Results.html().template("/views/layout/login.ftl.html").render("msg", userName);
        }
    }

    @javax.inject.Inject
    public Result logout(Context context) {
        context.getSession().clear();
        String msg = "User session invalidated, log in again";
        return Results.html().template("views/layout/login.ftl.html").render("msg", msg);

    }

}
