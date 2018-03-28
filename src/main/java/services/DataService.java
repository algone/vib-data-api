/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import static com.mongodb.client.model.Projections.include;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.inject.Singleton;
import model.Host;
import model.ParentUnit;
import model.Review;
import model.Unit;
import model.VibandaImage;
import net.binggl.ninja.mongodb.MongoDB;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.lifecycle.Dispose;
import ninja.lifecycle.Start;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author algone
 */
@Singleton
public class DataService implements Service {

//    final static Logger LOG = LoggerFactory.getLogger(DataService.class);
    @Inject
    org.slf4j.Logger LOG;
    @Inject
    private MongoDB mongoDB;
    private Datastore ds;

    @Start(order = 90)
    public void startService() {
        //do something     
        System.out.println("Data service started....");
    }

    @Dispose(order = 90)
    public void stopService() {
        //do something
        System.out.println("Stoping data service...");
    }

    public Result getCount(Context ctx) {
        return Results.json();
    }

    @Override
    public void addParent(ParentUnit vpu) {
        this.mongoDB.getMorphia().getMapper().getOptions().setStoreEmpties(true);
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.save(vpu);
    }

    @Override
    public void updateParent(ParentUnit parent) {
//        query = ds.createQuery(UserData.class).field("uUid").equal("1234");
//ops = ds.createUpdateOperations(UserData.class).set("status", "active");
//
//ds.update(query, ops);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteParent(String parentId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> parentToDel = ds.createQuery(ParentUnit.class).filter("id =", parentId);
        String id = parentToDel.get().getId();

        Query<Unit> query = ds.createQuery(Unit.class).field("unitParentId").equal(id);
        ds.findAndDelete(query);
        ParentUnit res = ds.findAndDelete(parentToDel);
        LOG.info("DELETED: " + res.getUnitName());
    }

    public Map<String, Object> getParentIds() {
        final Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        List<ParentUnit> parents = query.asList();
//        Map<String, Object> data = new HashMap<>();
        Map<String, Object> parentIds = new HashMap<>();
        parents.forEach((parent) -> {

            parentIds.put(parent.getId(), parent);
        });
        return parentIds;
    }

    public List<String> getUnitIds() {
        List<Unit> units = getAllUnits();
        List<String> unitIds = new ArrayList<>();

        units.forEach((parent) -> {
            System.out.println("adding ID: " + parent.getId());
            unitIds.add(parent.getId());

        });

        System.out.println("size: " + unitIds.size());
        return unitIds;
    }

    public List<Document> getCounties() {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");

        MongoCollection<Document> countiesCol = ds.getMongo().getDatabase("mongolab-amazon-vibanda").getCollection("counties");

        List<Document> foundDocument = countiesCol.find().projection(include("ke_counties"))
                .into(new ArrayList<>());

        return foundDocument;
    }

    public List<Document> getDestinations() {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");

        MongoCollection<Document> destinations = ds.getMongo().getDatabase("mongolab-amazon-vibanda").getCollection("destinations");

        List<Document> allDestinations = destinations.find().projection(include("vibanda_regions"))
                .into(new ArrayList<>());

        return allDestinations;
    }

    public List<Document> getTopDestinations() {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");

        MongoCollection<Document> destinations = ds.getMongo().getDatabase("mongolab-amazon-vibanda").getCollection("destinations");

        List<Document> topDestinations = destinations.find().into(new ArrayList<>());
// List<Document> countyDocs = (List<Document>)topDestinations.get("vibanda_regions");
        return topDestinations;
    }

    @Override
    public void saveImage(VibandaImage img) {
        System.out.println("Persisting image to mongolab....");
        this.mongoDB.getMorphia().getMapper().getOptions().setStoreEmpties(true);
         this.mongoDB.getMorphia().getMapper().getOptions().setStoreNulls(true);
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.save(img);

        Query<Unit> updateQuery = ds.createQuery(Unit.class).field("id").equal(img.getUnitId());

        if (updateQuery.get() != null) {

            Unit unit = updateQuery.get();

            if (img.isIsCoverImage()) {
                Query<ParentUnit> parentQuery = ds.createQuery(ParentUnit.class).field("id").equal(unit.getUnitParentId());
                UpdateOperations<ParentUnit> ops = ds.createUpdateOperations(ParentUnit.class).addToSet("parentImages", img);
                ds.update(parentQuery, ops);
            }

            UpdateOperations<Unit> ops = ds.createUpdateOperations(Unit.class).addToSet("unitImages", img);
            ds.update(updateQuery, ops);

        }

    }

    @Override
    public List<ParentUnit> getAllParents() {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> query = ds.createQuery(ParentUnit.class);

        List<ParentUnit> parents = query.asList();
        return parents;
    }

    public List<ParentUnit> getHostParentUnits(String hostId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        Query<ParentUnit> result = query.field("ownerID").equal(hostId);

        List<ParentUnit> parents = result.asList();
        return parents;
    }

    public ObjectId uploadToGridFS(String filePath, String fileName) {
        System.out.println("Calling upload...");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        ObjectId fileId = null;
        try {
            MongoDatabase db = this.mongoDB.getMongoClient().getDatabase("mongolab-amazon-vibanda");
            GridFSBucket gridBucket = GridFSBuckets.create(db, "vibanda_imgdb");
            InputStream inputStream = new FileInputStream(new File(filePath));
            // Create some custom options
            GridFSUploadOptions uploadOptions = new GridFSUploadOptions()
                    .chunkSizeBytes(1024)
                    .metadata(new Document("type", "image")
                            .append("upload_date", format.parse("2016-09-01T00:00:00Z"))
                            .append("content_type", "image/jpg"));

            fileId = gridBucket.uploadFromStream(fileName, inputStream, uploadOptions);

        } catch (Exception e) {
        } finally {
        }
        return fileId;
    }

    public void storeReview(Review rev, String identifier, String revType) {
        this.mongoDB.getMorphia().getMapper().getOptions().setStoreEmpties(true);
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        if (revType.equalsIgnoreCase("Host")) {
            Query<Host> updateQuery = ds.createQuery(Host.class).field("email").equal(identifier);
            UpdateOperations<Host> ops = ds.createUpdateOperations(Host.class).addToSet("hostReviews", rev);
            ds.update(updateQuery, ops);
        } else {
            Query<Unit> updateQuery = ds.createQuery(Unit.class).field("id").equal(identifier);

            UpdateOperations<Unit> ops = ds.createUpdateOperations(Unit.class).addToSet("unitReviews", rev);
            ds.update(updateQuery, ops);
        }
    }

    @Override
    public List<Unit> getAllUnits() {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        return query.asList();
    }

    public List<Host> getAllHosts() {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Host> query = ds.createQuery(Host.class);
        return query.asList();
    }

    public Host getHostByHostId(String hostEmail) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Host> query = ds.createQuery(Host.class);
        Query<Host> result = query.field("email").equal(hostEmail);
        return result.get();
    }

    public Host getHost(String id) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Host> query = ds.createQuery(Host.class).field("id").equal(id);

        LOG.info("Getting host for id : " + id + " Found :" + query.get().getId());

        return query.get();
    }

    @Override
    public void addUnit(Unit unit) {
        String parentId = unit.getUnitParentId();
        LOG.info("UNIT PARENT ID: " + parentId);
        ParentUnit parent = findParentUnitById(parentId);
        LOG.info("PARENT ID: " + parentId);
        unit.setLocation(parent.getLocation());
        unit.setEcorated(parent.getEcorating());
        unit.setParentType(parent.getParentType());
        unit.setUnitStyle(parent.getStyle());
        this.mongoDB.getMorphia().getMapper().getOptions().setStoreEmpties(true);
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.save(unit);
        Query<ParentUnit> updateQuery = ds.createQuery(ParentUnit.class).field("id").equal(parentId);
        UpdateOperations<ParentUnit> ops = ds.createUpdateOperations(ParentUnit.class).addToSet("rentalUnits", unit.getId());
        ds.update(updateQuery, ops);
    }

    @Override
    public void updateUnit(Unit unit) {

//        query = ds.createQuery(UserData.class).field("uUid").equal("1234");
//        ops = ds.createUpdateOperations(UserData.class).set("status", "active");
//
//        ds.update(query, ops);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteUnit(String unitId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<Unit> unitToDel = ds.createQuery(Unit.class).filter("id =", unitId);
        String id = unitToDel.get().getId();

        Query<VibandaImage> query = ds.createQuery(VibandaImage.class).field("unitId").equal(id);
        ds.findAndDelete(query);
        Unit res = ds.findAndDelete(unitToDel);
        LOG.info("DELETED: " + res.getUnitHeading());

    }

    @Override
    public ParentUnit findParentUnitById(String parentId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        Query<ParentUnit> result = query.field("id").equal(parentId);
//        LOG.info("FOUND PARENT: " + result.get().getId());
        return result.get();
    }

    public ParentUnit findParentByUnitId(String unitId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        Query<Unit> result = query.field("id").equal(unitId);

        Unit foundUnit = result.get();
        LOG.info("FOUND UNIT: " + foundUnit.getId() + " with PARENT ID: " + foundUnit.getUnitParentId());

        ParentUnit parent = findParentUnitById(foundUnit.getUnitParentId());
        return parent;
    }

    @Override
    public Unit findUnitById(String unitId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        Query<Unit> result = query.field("id").equal(unitId);
        return result.get();
    }

    public List<Unit> findUnitsByHostId(String email) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        LOG.info("HOST ID: " + email);
        Query<ParentUnit> query = ds.createQuery(ParentUnit.class).field("ownerID").equal(email);
        List<Unit> hostUnits = new ArrayList<>();

        for (ParentUnit parentUnit : query) {
            List<Unit> units = findUnitsByParentId(parentUnit.getId());
            hostUnits.addAll(units);
        }

        return hostUnits;
    }

    @Override
    public List<VibandaImage> findUnitImagesById(String unitId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<VibandaImage> query = ds.createQuery(VibandaImage.class);
        Query<VibandaImage> result = query.field("unitId").equal(unitId);
        return result.asList();
    }

    @Override
    public List<Unit> findUnitsByParentId(String parentId) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        LOG.info("INCOMING ID: " + parentId);
        Query<Unit> result = query.field("unitParentId").equal(parentId);
        return result.asList();
    }

    @Override
    public List<Unit> searchUnits(JsonNode jsonData) {
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.ensureIndexes();
        LOG.debug("JSON data: " + jsonData.textValue());
//        JsonNode jsonNode1 = jsonData.get("place");
        JsonNode locationNode = jsonData.get("location");
//        String place = jsonNode1.textValue();
        String county = locationNode.get("countyName").textValue();
        List<Unit> units = ds.createQuery(Unit.class)
                .search(county)
                .order("_id")
                .asList();

        return units;
    }

    public void addHost(Host host) {
        this.mongoDB.getMorphia().getMapper().getOptions().setStoreEmpties(true);
        this.mongoDB.getMorphia().getMapper().getOptions().setStoreNulls(true);
        ds = this.mongoDB.getMorphia().createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.save(host);
    }

    public List<Unit> searchUnits(String searchWord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
