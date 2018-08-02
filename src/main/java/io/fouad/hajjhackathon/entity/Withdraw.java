package io.fouad.hajjhackathon.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "withdraw")
@NamedQuery(
        name="Withdraw.getByCustomerId",
        query="SELECT withdraw FROM Withdraw withdraw WHERE withdraw.customerId = :customerId ORDER BY withdraw.timestamp DESC"
)
public class Withdraw
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "vm_id")
    private int vmId;

    @Column(name = "food_id")
    private int foodId;

    private Long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}