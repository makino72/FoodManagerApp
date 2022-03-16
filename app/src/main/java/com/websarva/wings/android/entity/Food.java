package com.websarva.wings.android.entity;

public class Food {

    private String foodName; //食材の名前
    private double foodStock; //在庫数
    private String foodStockUnit; //在庫の単位
    private double purchaseCount; //購入する時の数(1/4切れをよく買うなら0.25といった感じ)
    private double usageCount; //在庫数を減らす時の数(1/8切れをよく使うなら0.125といった感じ)

    public Food(String foodName, double foodStock, String foodStockUnit, double purchaseCount, double usageCount) {
        this.foodName = foodName;
        this.foodStock = foodStock;
        this.foodStockUnit = foodStockUnit;
        this.purchaseCount = purchaseCount;
        this.usageCount = usageCount;
    }

    /******************
    getterとsetter
    *******************/
    public String getFoodName() {
        return foodName;
    }

    public double getFoodStock() {
        return foodStock;
    }

    public String getFoodStockUnit() {
        return foodStockUnit;
    }

    public double getPurchaseCount() {
        return purchaseCount;
    }

    public double getUsageCount() {
        return usageCount;
    }
}
