package com.example.expense.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExpensesType {
    private int typeId;
    private String typeName;
    private double typeLimit;


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getTypeLimit() {
        return typeLimit;
    }

    public void setTypeLimit(double typeLimit) {
        this.typeLimit = typeLimit;
    }

    public ExpensesType(int typeId, String typeName, double typeLimit) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeLimit = typeLimit;
    }


    public static ArrayList<ExpensesType> getExpensesTypes(JSONArray jsonArray) {
        ArrayList<ExpensesType> expensesTypes = new ArrayList<>();



        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ExpensesType expensesType = new ExpensesType(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getDouble("limit"));

                expensesTypes.add(expensesType);
            }
        }catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return expensesTypes;

    }}
