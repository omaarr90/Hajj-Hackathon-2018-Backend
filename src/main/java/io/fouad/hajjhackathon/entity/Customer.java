package io.fouad.hajjhackathon.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String barcode;

    @Column(name = "display_name_ar")
    private String displayNameAr;

    @Column(name = "display_name_en")
    private String displayNameEn;

    private Long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDisplayNameAr() {
        return displayNameAr;
    }

    public void setDisplayNameAr(String displayNameAr) {
        this.displayNameAr = displayNameAr;
    }

    public String getDisplayNameEn() {
        return displayNameEn;
    }

    public void setDisplayNameEn(String displayNameEn) {
        this.displayNameEn = displayNameEn;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}