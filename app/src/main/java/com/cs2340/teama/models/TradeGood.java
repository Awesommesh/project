package com.cs2340.teama.models;

import android.util.Log;

import com.cs2340.teama.models.enums.GoodType;
import com.cs2340.teama.models.realm.TradeGoodModel;

public class TradeGood {

    private double value;
    private int quantity;
    private GoodType goodType;

    public TradeGood(TradeGoodModel tgm) {
        this.value = tgm.getValue();
        this.quantity = tgm.getQuantity();
        this.goodType = GoodType.valueOf(tgm.getType());
    }

    public TradeGood(double value, GoodType gT, int quantity) {
        this.value = value;
        this.goodType = gT;
        this.quantity = quantity;
        Log.d("Edit","Created good " + gT + " with value " + value +" and quantity " + quantity);
    }

    public double getValue() {
        return value;
    }

    public int getVolume() {
        return quantity;
    }

    public GoodType getGoodType() {
        return goodType;
    }

    public void incrementVolume(int volAdded) {
        quantity += volAdded;
    }

    public boolean decrementVolume(int volRemoved) {
        if (quantity < volRemoved) {
            return false;
        }
        quantity -= volRemoved;
        return true;
    }

    public boolean inStock() {
        return quantity > 0;
    }

    /**
     * Note I made the Ç the money sign in this game for fun
     *
     * @return a string representation of the TradeGood
     */
    @Override
    public String toString() {
        return goodType + " with price Ç" + value + " takes up " + quantity + " units of cargo space.";
    }
}
