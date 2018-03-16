/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


/**
 *
 * @author A4372949
 */
@Embeddable @Access(AccessType.FIELD)
public class RentalInfo implements Serializable {

    private double serviceFee;
    private double securityDeposit;
    private double shortTermRentalPrice;
    private double longTermRentalPrice;
    private String cancellationPolicy;
    private String currencyType = "US Dollars";
    private String includeMeals;
    private double childDiscount;
    private double perExtraGuestCharge;


    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public double getShortTermRentalPrice() {
        return shortTermRentalPrice;
    }

    public void setShortTermRentalPrice(double shortTermRentalPrice) {
        this.shortTermRentalPrice = shortTermRentalPrice;
    }

    public double getLongTermRentalPrice() {
        return longTermRentalPrice;
    }

    public void setLongTermRentalPrice(double longTermRentalPrice) {
        this.longTermRentalPrice = longTermRentalPrice;
    }
    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getIncludeMeals() {
        return includeMeals;
    }

    public void setIncludeMeals(String includeMeals) {
        this.includeMeals = includeMeals;
    }



    public double isChildDiscount() {
        return childDiscount;
    }

    public void setChildDiscount(double childDiscount) {
        this.childDiscount = childDiscount;
    }

    public double getPerExtraGuestCharge() {
        return perExtraGuestCharge;
    }

    public void setPerExtraGuestCharge(double perExtraGuestCharge) {
        this.perExtraGuestCharge = perExtraGuestCharge;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

}
