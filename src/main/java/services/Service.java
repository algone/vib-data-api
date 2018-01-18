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

    public void addParent(ParentUnit parent);

    public void updateParent(ParentUnit parent);

    public void deleteParent(long parentId);

    public void addUnit(Unit unit);

    public void updateUnit(Unit unit);

    public void deleteUnit(String unitId);

    /**
     *
     * @param parentId
     * @return
     */
    public ParentUnit findParent(String parentId);
    public List<Unit> findUnitsByParentId(String parentId);
    public Unit findUnit(String unitId);
    public ParentUnit findUnitImages(String unitId);
    public List<ParentUnit> getAllParents();
    public List<Unit> getAllUnits();
    public void saveImage(VibandaImage img);
}
