package com.example.rentalapp.Entities;

public class Payment {

    private int id;
    private int tenantId;
    private int propertyId;
    private String date; // Payment date (stored as TEXT, e.g., "YYYY-MM-DD")
    private double amount;

    public Payment() {}

    public Payment(int id, int tenantId, int propertyId, String date, double amount) {
        this.id = id;
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", propertyId=" + propertyId +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}
