package io.fouad.hajjhackathon.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "food")
@NamedQueries({
   @NamedQuery(
       name="Food.getFoodListByVendingMachineId",
       query="SELECT food FROM Food food WHERE food.vmId = :vmId"
   ),
   @NamedQuery(
       name="Food.getFoodById",
       query="SELECT food FROM Food food WHERE food.id = :id"
   )
})
public class Food
{
    @Id
    private Integer id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    private String type;

    @Column(name = "picture_url")
    private String pictureUrl;

    private int calories;
    private int weight;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "vm_id")
    private Integer vmId;

    private Long timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getVmId() {
        return vmId;
    }

    public void setVmId(Integer vmId) {
        this.vmId = vmId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}