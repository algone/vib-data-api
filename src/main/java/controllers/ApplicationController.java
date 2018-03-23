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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.DataService;
import services.VibandaAuthService;
import services.VibandaImageService;

@FileProvider(DiskFileItemProvider.class)
@Singleton
public class ApplicationController {

    Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
    @Inject
    DataService dbService;
    @Inject
    VibandaImageService imgService;
    @Inject
    VibandaAuthService authService;
    @Inject
    ReverseRouter reverseRouter;
    List<Unit> units = new ArrayList<>();
    List<VibandaImage> unitImages = new ArrayList<>();

    @FilterWith(AuthenticationFilter.class)
    public Result index(Context context) {
        LOG.info("We are here.....");
        String userId = context.getSession().get("userId");
        List<ParentUnit> vpus = dbService.getHostParentUnits(userId);
        String userName = context.getSession().get("userName");
        return Results.html().template("views/ApplicationController/index.ftl.html").render("host", userName).render("parents", vpus);
    }

    public Result showParentUnitForm(Context context) {
        List<Document> counties = dbService.getCounties();
        List<Document> countyDocs = null;
        for (Document county : counties) {
            if (county.get("ke_counties") != null) {
                countyDocs = (List<Document>) county.get("ke_counties");
                break;
            }
        }
        String userName = context.getSession().get("userName");
        return Results.html().template("views/ApplicationController/parentUnitUpload.ftl.html").render("counties", countyDocs).render("host", userName);
    }

    public Result showLoginForm() {
        LOG.info("SHOWING LOGIN FORM");
        return Results.html().template("views/layout/login.ftl.html");
    }

    public Result showRegisterForm() {
        return Results.html().template("views/layout/register.ftl.html");
    }

    public Result showUnitForm(Context context) {
        String userId = context.getSession().get("userId");
        List<ParentUnit> hostPus = dbService.getHostParentUnits(userId);
        return Results.html().template("views/ApplicationController/unitUpload.ftl.html").render("data", hostPus).render("host", context.getSession().get("userName"));
    }

    public Result showImageUploadForm(Context context) {
        List<Unit> unitIds = dbService.getAllUnits();
        System.out.println("Showing image upload form");
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("units", unitIds).render("host", context.getSession().get("userName"));

    }

    public Result showReviewForm(Context context) {
        boolean isReviewingUnit = context.getRequestPath().contains("/form/unit/review");
        if (isReviewingUnit) {
            System.out.println("Reviewing a UNIT");
            List<Unit> allUnits = dbService.getAllUnits();
            return Results.html().template("views/ApplicationController/reviewForm.ftl.html")
                    .render("units", allUnits).render("title", "Unit")
                    .render("host", context.getSession().get("userName"));
        } else {
            System.out.println("Reviewing a HOST");
            List<Host> hosts = dbService.getAllHosts();
            return Results.html().template("views/ApplicationController/reviewForm.ftl.html")
                    .render("hosts", hosts).render("title", "Host")
                    .render("host", context.getSession().get("userName"));
        }

    }

    public Result addUnit(Context context) {
        ParamsExtrator pe = new ParamsExtrator(context);
        Unit vUnit = pe.getUnit();
        vUnit.getUnitImages().addAll(unitImages);
        unitImages.clear();
        units.add(vUnit);
        return Results.json().render(units);
    }

    @UnitOfWork
    public Result listAll(Context context) {
        List<ParentUnit> vpus = dbService.getAllParents();
        return Results.json().render(vpus);

    }

    public Result uploadUnitImage(Context context,
            @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("imageDescription") String imageDescription) throws Exception {

        VibandaImage img = saveUnitImage(unitImageFile, imageDescription);
        unitImages.add(img);
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
        Host user = dbService.getHost(email);
        if (user != null) {
            LOG.info("User exists: " + user.getUserName() + " PASS :" + user.getPassword());
            boolean isValid = authService.authenticate(pass, user.getPassword());
            LOG.info("User is Authenticated: " + isValid);
            if (isValid) {
                authService.login(context, email, isValid);
                context.getSession().put("userName", user.getUserName());
                return Results.redirect("/");
            } else {
                LOG.info("Wrong credentials ... redirecting to login");
                return Results.html().template("/views/layout/login.ftl.html");
            }
        } else {
            LOG.info("User does not exists: " + email + "... go register");
            return Results.html().template("/views/layout/register.ftl.html");
        }

//        System.out.println("Authenticated user ...." + context.getSession().get(NinjaKey.AUTHENTICATED_USER.get()));
//        if (user != null) {
//
//            System.out.println("User already registered: " + user.getEmail());
//            //User exists ...go to demo page
//            System.out.println("Authenticating user ....");
//            if (user.getPassword().matches(pass)) {
//                System.out.println("User is authorized ....creating session");
//                if (context.getSession().get("userId") == null) {
//                    System.out.println("User does not have an active session....setting user session and redirecting to index.html");
//                    context.getSession().put("userId", user.getEmail());
//                    context.getSession().put("userName", user.getUserName());
//                } else {
//                    System.out.println("User already have an active session ....redirecting to index.html");
//                }
//                return Results.redirect("/");
//            } else {
//                System.out.println("User not authorized ....check credentials ... redirecting to login");
//                return Results.html().template("/views/layout/login.ftl.html");
//            }
//
//        } else {
//            //User does not exists ... go to login page
//            System.out.println("User not found: User does not exists ... go to login page");
//            context.getSession().clear();
//            return Results.html().template("/views/layout/register.ftl.html");
//        }
    }

    @Transactional
    public Result register(@Param("userName") String userName,
            @Param("email") String email,
            @Param("password") String pass,
            @Param("firstName") String fname,
            @Param("lastName") String lname,
            Context context) {
        Host user = dbService.getHost(email);

        if (user != null) {
            //User exists ...go to login page and log in

            return Results.html().template("/views/layout/signin.ftl.html").render("msg", "User " + userName + " already exists, got to login");
        } else {
            //User does not exists ... go to login page
            Host host = new Host();
            host.setUserName(userName);
            host.setEmail(email);
            host.setPassword(authService.getHashedPassword(pass));
            host.setFirstName(fname);
            host.setLastName(lname);
            host.setWhenJoined(ZonedDateTime.now().toString());

            dbService.addHost(host);
            return Results.html().template("/views/layout/login.ftl.html").render("msg", userName);
        }
    }

    public Result logout(Context context) {
//        context.getSession().clear();
        LOG.info("User session invalidated, log in again");
        authService.logout(context);
//        String msg = "User session invalidated, log in again";

        return Results.html().redirect("/form/login");

    }

}
