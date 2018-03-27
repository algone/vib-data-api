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
package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;
import controllers.DatabaseController;
import controllers.ParentController;
import controllers.UnitController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        /**
         * Root, defaults to index page
         */
        router.GET().route("/").with(ApplicationController::index);

        router.POST().route("/api/images/upload2").with(DatabaseController::uploadImage);
        router.GET().route("/form/uploadImage").with(ApplicationController::showImageUploadForm);
        router.GET().route("/form/addParent").with(ApplicationController::showParentUnitForm);
        router.GET().route("/form/addUnit").with(ApplicationController::showUnitForm);
        router.GET().route("/form/unit/review").with(ApplicationController::showReviewForm);


        

        //Images api
        router.GET().route("/api/images/{unitId}").with(DatabaseController::findUnitImages);

//        router.GET().route("/create").with(ApplicationController::createParentUnit);

        
        //Login and logout
        router.GET().route("/form/login").with(ApplicationController::showLoginForm);
        router.GET().route("/form/register").with(ApplicationController::showRegisterForm);
        router.GET().route("/form/host/review").with(ApplicationController::showReviewForm);

        
        //Host login/registration/profileupdate/reviews/ratings
        router.POST().route("/user/login").with(ApplicationController::login);
        router.POST().route("/user/register").with(ApplicationController::register);
        router.POST().route("/api/{revtype}/review").with(DatabaseController::addReview);
        router.POST().route("/user/logout").with(ApplicationController::logout);

        
        //Search
        router.POST().route("/api/search").with(DatabaseController::search);
        router.GET().route("/api/counties").with(DatabaseController::findCounties);
        router.GET().route("/api/destinations/all").with(DatabaseController::findDestinations);
        router.GET().route("/api/destinations/top").with(DatabaseController::findTopDestinations);
        router.GET().route("/api/hosts").with(DatabaseController::listAllHosts);
        router.GET().route("/api/hosts/{hostId}").with(DatabaseController::findHost);
        router.GET().route("/api/hostunits/{hostId}").with(DatabaseController::findHostUnits);


        ///////////////////////////////////////////////////////////////////////
        // Parents API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/api/parents/add").with(ParentController::addParent);        
        router.GET().route("/api/parents/all").with(ParentController::getAllParents);
        router.GET().route("/api/parent/{parentId}").with(ParentController::getParent);
        router.GET().route("/api/parents/host/{hostId}").with(ParentController::getParentByHostId);
        router.GET().route("/api/parents/unit/{unitId}").with(ParentController::getParentByUnitId);
        router.DELETE().route("/api/parents/{parentId}/delete").with(ParentController::deleteParent);
        
        ///////////////////////////////////////////////////////////////////////
        // Units API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/api/units/add").with(UnitController::addUnit);
        router.GET().route("/api/units/all").with(UnitController::getAllUnits);
        router.GET().route("/api/unit/{unitId}").with(UnitController::getUnit);
        router.GET().route("/api/units/parent/{parentId}").with(UnitController::getUnitsByParentId);
        router.GET().route("/api/units/unit/{hostId}").with(UnitController::getUnitsByHostId);
        router.DELETE().route("/api/units/{unitId}/delete").with(UnitController::deleteUnit);
        
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        router.GET().route("/assets/img/images/{fileName: .*}").with(AssetsController.class, "serveStatic");

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController::index);
    }

}
////