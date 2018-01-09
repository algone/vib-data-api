/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import model.ParentUnit;
import model.Unit;

/**
 *
 * @author A4372949
 */
public interface Service {
    public void addParent(ParentUnit parent);
    public void updateParent(ParentUnit parent);
    public void deleteParent(long parentId);
    public ParentUnit findParent(long parentId);
    public List<ParentUnit> getAllParents();
    public List<Unit> getAllUnits();
    
}
