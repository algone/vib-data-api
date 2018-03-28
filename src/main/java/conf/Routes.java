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
import controllers.DestinationsController;
import controllers.HostController;
import controllers.ParentController;
import controllers.UnitController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        /**
         * Root, defaults to index page
         */
        router.GET().route("/").with(ApplicationController::index);
        router.GET().route("/form/uploadImage").with(ApplicationController::showImageUploadForm);
        router.GET().route("/form/addParent").with(ApplicationController::showParentUnitForm);
        router.GET().route("/form/addUnit").with(ApplicationController::showUnitForm);
        router.GET().route("/form/unit/review").with(ApplicationController::showReviewForm);
        router.GET().route("/form/login").with(ApplicationController::showLoginForm);
        router.GET().route("/form/register").with(ApplicationController::showRegisterForm);
        router.GET().route("/form/host/review").with(ApplicationController::showReviewForm);
        
        ///////////////////////////////////////////////////////////////////////
        // Images API
        /////////////////////////////////////////////////////////////////////// 
        router.GET().route("/api/images/{unitId}").with(DatabaseController::findUnitImages);
        router.POST().route("/api/images/upload").with(DatabaseController::uploadImage);

        ///////////////////////////////////////////////////////////////////////
        // Dashboard User Login, Logout and Register API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/user/login").with(ApplicationController::login);
        router.POST().route("/user/register").with(ApplicationController::register);
        router.GET().route("/user/logout").with(ApplicationController::logout);

        ///////////////////////////////////////////////////////////////////////
        // Destinations API
        /////////////////////////////////////////////////////////////////////// 
        router.GET().route("/api/destinations/all").with(DestinationsController::findDestinations);
        router.GET().route("/api/destinations/top").with(DestinationsController::findTopDestinations);
 
        
        ///////////////////////////////////////////////////////////////////////
        // Review API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/api/{revtype}/review").with(DatabaseController::addReview);  

        ///////////////////////////////////////////////////////////////////////
        // Search API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/api/search").with(DatabaseController::search);
        router.GET().route("/api/counties").with(DatabaseController::findCounties);
      
        ///////////////////////////////////////////////////////////////////////
        // Host API
        /////////////////////////////////////////////////////////////////////// 
        router.GET().route("/api/hosts/all").with(HostController::listAllHosts);
        router.GET().route("/api/hosts/{hostId}").with(HostController::findHostByUsername);
        router.GET().route("/api/hosts/{id}/find").with(HostController::getHost);
        router.GET().route("/api/hostunits/{hostId}").with(HostController::findHostUnits);


        ///////////////////////////////////////////////////////////////////////
        // Parents API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/api/parents/add").with(ParentController::addParent);
        router.GET().route("/api/parents/all").with(ParentController::getAllParents);
        router.GET().route("/api/parents/{parentId}").with(ParentController::getParent);
        router.GET().route("/api/parents/host/{hostId}").with(ParentController::getParentByHostId);
        router.GET().route("/api/parents/unit/{unitId}").with(ParentController::getParentByUnitId);
        router.GET().route("/api/parents/{parentId}/delete").with(ParentController::deleteParent);

        ///////////////////////////////////////////////////////////////////////
        // Units API
        /////////////////////////////////////////////////////////////////////// 
        router.POST().route("/api/units/add").with(UnitController::addUnit);
        router.GET().route("/api/units/all").with(UnitController::getAllUnits);
        router.GET().route("/api/units/{unitId}").with(UnitController::getUnit);
        router.GET().route("/api/units/parent/{parentId}").with(UnitController::getUnitsByParentId);
        router.GET().route("/api/units/host/{hostId}").with(UnitController::getUnitsByHostId);
        router.GET().route("/api/units/{unitId}/delete").with(UnitController::deleteUnit);

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        router.GET().route("/assets/img/images/{fileName: .*}").with(AssetsController.class, "serveStatic");

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/.*").with(ApplicationController::index);
    }

}
