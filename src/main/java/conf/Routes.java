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

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {

        router.GET().route("/").with(ApplicationController::index);
        router.POST().route("/api/parents/add").with(DatabaseController::addParent);
        router.POST().route("/api/units/add").with(DatabaseController::addUnit);
        router.DELETE().route("/api/parents/delete/{parentId}").with(DatabaseController::deleteParent);
        router.GET().route("/api/parents/find/{parentId}").with(DatabaseController::findParent);
        router.POST().route("/api/images/upload").with(DatabaseController::addImage);
        router.POST().route("/api/images/upload2").with(DatabaseController::uploadImage);
        router.GET().route("/api/parents").with(DatabaseController::listAll);
        router.GET().route("/api/units").with(ApplicationController::findAllUnits);

        router.GET().route("/create").with(ApplicationController::createParentUnit);
        router.GET().route("/form/uploadImage").with(ApplicationController::showImageUploadForm);
          router.GET().route("/form/addParent").with(ApplicationController::showParentUnitForm);
            router.GET().route("/form/addUnit").with(ApplicationController::showUnitForm);
              router.GET().route("/test123").with(ApplicationController::showUpload);

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
