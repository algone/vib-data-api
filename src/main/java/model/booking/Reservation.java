/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.booking;

import java.util.List;
import model.Unit;

/**
 *
 * @author A4372949
 */
public class Reservation {
    String dateIn;
    String dateout;
    String madeBy;
    List<Guest> guests;
    List<Unit> occupiedUnits;
    
}
