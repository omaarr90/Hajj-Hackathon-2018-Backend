package io.fouad.hajjhackathon.entity;

import java.util.List;

public class FoodSupplyRequest
{
    private int vmId;
    private List<Food> foodList;

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public String toString() {
        return "FoodSupplyRequest{" +
                "vmId='" + vmId + '\'' +
                ", foodList=" + foodList +
                '}';
    }
}