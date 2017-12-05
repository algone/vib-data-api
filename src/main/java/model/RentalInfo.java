/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author A4372949
 */
public class RentalInfo {

    private RentalType rentalType = RentalType.SHORT_TERM;
    private boolean subleasingAllowed;
    private double adminFee;
    private double securityDeposit;
    private double shortTermRentalPrice;
    private double longTermRentalPrice;
    private boolean cancellationAllowed;
    private double cancellationFee;
    private String currencyType = "US Dollars";
    private boolean includeMeals;
    private boolean childDiscount;
    private double perExtraGuestCharge;

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    public boolean isSubleasingAllowed() {
        return subleasingAllowed;
    }

    public void setSubleasingAllowed(boolean subleasingAllowed) {
        this.subleasingAllowed = subleasingAllowed;
    }

    public double getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(double adminFee) {
        this.adminFee = adminFee;
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

    public boolean isCancellationAllowed() {
        return cancellationAllowed;
    }

    public void setCancellationAllowed(boolean cancellationAllowed) {
        this.cancellationAllowed = cancellationAllowed;
    }

    public double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public boolean isIncludeMeals() {
        return includeMeals;
    }

    public void setIncludeMeals(boolean includeMeals) {
        this.includeMeals = includeMeals;
    }

    public boolean isChildDiscount() {
        return childDiscount;
    }

    public void setChildDiscount(boolean childDiscount) {
        this.childDiscount = childDiscount;
    }

    public double getPerExtraGuestCharge() {
        return perExtraGuestCharge;
    }

    public void setPerExtraGuestCharge(double perExtraGuestCharge) {
        this.perExtraGuestCharge = perExtraGuestCharge;
    }

}
