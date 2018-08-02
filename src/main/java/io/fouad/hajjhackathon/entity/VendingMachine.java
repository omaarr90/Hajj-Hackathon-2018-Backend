package io.fouad.hajjhackathon.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "vm")
@NamedQueries({
    @NamedQuery(
        name="VendingMachine.getAll",
        query="SELECT vm FROM VendingMachine vm"
    ),
    @NamedQuery(
        name="VendingMachine.getById",
        query="SELECT vm FROM VendingMachine vm WHERE vm.id = :id"
    ),
    @NamedQuery(
        name="VendingMachine.getById",
        query="SELECT vm FROM VendingMachine vm WHERE vm.id = :id"
    )})
public class VendingMachine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "address_ar")
    private String addressAr;

    @Column(name = "address_en")
    private String addressEn;

    private double longitude;
    private double latitude;
    private Long timestamp;

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
}