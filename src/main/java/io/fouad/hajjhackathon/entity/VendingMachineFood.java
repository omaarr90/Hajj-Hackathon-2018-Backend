package io.fouad.hajjhackathon.entity;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VendingMachineFood
{
    private int id;
    private String name;
    private String addressAr;
    private String addressEn;
    private double longitude;
    private double latitude;
    private Long timestamp;

    private List<Food> foodList;

    public VendingMachineFood(int id, String name, String addressAr, String addressEn, double longitude, double latitude, Long timestamp, List<Food> foodList) {
        this.id = id;
        this.name = name;
        this.addressAr = addressAr;
        this.addressEn = addressEn;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.foodList = foodList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressAr() {
        return addressAr;
    }

    public void setAddressAr(String addressAr) {
        this.addressAr = addressAr;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendingMachineFood that = (VendingMachineFood) o;
        return id == that.id &&
                Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(addressAr, that.addressAr) &&
                Objects.equals(addressEn, that.addressEn) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(foodList, that.foodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, addressAr, addressEn, longitude, latitude, timestamp, foodList);
    }

    @Override
    public String toString() {
        return "VendingMachineFood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressAr='" + addressAr + '\'' +
                ", addressEn='" + addressEn + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", timestamp=" + timestamp +
                ", foodList=" + foodList +
                '}';
    }
}