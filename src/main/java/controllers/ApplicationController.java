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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Host;
import model.ParentUnit;
import model.Unit;
import model.VibandaImage;

import ninja.Context;
import ninja.FilterWith;
import ninja.ReverseRouter;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileProvider;
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
        String userId = authService.getAuthenticatedUser(context);

        LOG.info("USER: " + userId);
        List<ParentUnit> vpus = dbService.getHostParentUnits(userId);
        String userName = context.getSession().get("userName");
        LOG.info("USER NAME: " + userName + " with " + vpus.size() + " parents");

        Host profile = dbService.getHostByHostId(userId);

        return Results.html().template("views/ApplicationController/index.ftl.html").render("host", userName).render("parents", vpus).render("profile", profile);
    }

    public Result showParentUnitForm(Context context) {
        List<Document> counties = dbService.getCounties();
        LOG.info("Retrieving counties.....");
        List<Document> countyDocs = null;
        for (Document county : counties) {
            if (county.get("ke_counties") != null) {
                LOG.info("Found counties.....");
                countyDocs = (List<Document>) county.get("ke_counties");
                break;
            }
        }
 
        return Results.html().template("views/ApplicationController/parentUnitUpload.ftl.html").render("counties", countyDocs);
    }

    public Result showLoginForm() {
        LOG.info("SHOWING LOGIN FORM");
        return Results.html().template("views/layout/login.ftl.html");
    }

    public Result showRegisterForm() {
        return Results.html().template("views/layout/register.ftl.html");
    }

    public Result showUnitForm(Context context) {
        List<ParentUnit> hostPus = dbService.getHostParentUnits(authService.getAuthenticatedUser(context));
        return Results.html().template("views/ApplicationController/unitUpload.ftl.html").render("data", hostPus);
    }

    public Result showImageUploadForm(Context context) {
        List<Unit> unitIds = dbService.findUnitsByHostId(authService.getAuthenticatedUser(context));
        System.out.println("Showing image upload form");
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("units", unitIds);

    }

    public Result showReviewForm(Context context) {
        boolean isReviewingUnit = context.getRequestPath().contains("/form/unit/review");
        if (isReviewingUnit) {
            System.out.println("Reviewing a UNIT");
            List<Unit> allUnits = dbService.getAllUnits();
            return Results.html().template("views/ApplicationController/reviewForm.ftl.html")
                    .render("units", allUnits).render("title", "Unit");
        } else {
            System.out.println("Reviewing a HOST");
            List<Host> hosts = dbService.getAllHosts();
            return Results.html().template("views/ApplicationController/reviewForm.ftl.html")
                    .render("hosts", hosts).render("title", "Host");
        }

    }

    @UnitOfWork
    public Result login(@Param("email") String email, @Param("password") String pass, Context context) {
        Host user = dbService.getHostByHostId(email);
        if (user != null) {

            boolean isValid = authService.authenticate(pass, user.getPassword());
            
            if (isValid) {
                LOG.info("User is Authenticated: " + isValid);
                authService.login(context, email, isValid);
                context.getSession().put("userName", user.getUserName());
//                return Results.redirect("/");
                return reverseRouter.with(ApplicationController::index).redirect();
            } else {
                LOG.info("Wrong credentials ... redirecting to login");
                return Results.html().template("/views/layout/login.ftl.html");
           
            }
        } else {
            LOG.info("User does not exists: " + email + "... go register");
//            return Results.html().template("/views/layout/register.ftl.html");
            return reverseRouter.with(ApplicationController::showRegisterForm).redirect();
        }
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

            LOG.info("User exists ...go to login page and log in");
//            return Results.html().template("/views/layout/signin.ftl.html").render("msg", "User " + userName + " already exists, got to login");
            return reverseRouter.with(ApplicationController::showLoginForm).redirect();
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
            return reverseRouter.with(ApplicationController::showLoginForm).redirect();
        }
    }

    public Result logout(Context context) {

        LOG.info("User session invalidated, log in again");
        authService.logout(context);
        return reverseRouter.with(ApplicationController::showLoginForm)
                .redirect();

    }

}
