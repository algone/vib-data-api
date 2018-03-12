/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import model.ParentUnit;
import model.Unit;
import model.VibandaImage;

/**
 *
 * @author A4372949
 */
public interface Service {

    /**
     *Adds a new @ParentUnit to mongodb.
     * @param parent
     */
    public void addParent(ParentUnit parent);

    /**
     *Updates an existing ParentUnit
     * @param parent
     */
    public void updateParent(ParentUnit parent);

    /**
     *Deletes an existing ParentUnit
     * @param parentId
     */
    public void deleteParent(long parentId);

    /**
     *Persists a new Vibanda Unit to mongodb
     * @param unit
     */
    public void addUnit(Unit unit);

    /**
     *Updates an existing Unit
     * @param unit
     */
    public void updateUnit(Unit unit);

    /**
     *Deletes an existing Unit from the DB. Implementations should ensure that
     * constituent 
     * @param unitId
     */
    public void deleteUnit(String unitId);

    /**
     *Retrieves a ParentUnit using the given ParentUnit Id
     * @param parentId
     * @return ParentUnit with Matching Id
     */
    public ParentUnit findParent(String parentId);

    /**
     *Lists all Units belonging to a ParentUnit with a given Id
     * @param parentId
     * @return List of Units
     */
    public List<Unit> findUnitsByParentId(String parentId);

    /**
     *Retrieves a Unit with a given id
     * @param unitId
     * @return
     */
    public Unit findUnit(String unitId);

    /**
     *Retrieves all images belonging to the Unit with a given Unit id
     * @param unitId
     * @return List of Images
     */
    public List<VibandaImage> findUnitImages(String unitId);

    /**
     *Retrieves all Parents Units in the DB
     * @return
     */
    public List<ParentUnit> getAllParents();

    /**
     *Retrieves all Units in the DB
     * @return
     */
    public List<Unit> getAllUnits();

    /**
     *Simultaneously Saves the Image to both Vibanda Images CDN(Cloudinary) and mongolab
     * @param img
     */
    public void saveImage(VibandaImage img);
    public void searchUnits(String jsonStr);
    
}
