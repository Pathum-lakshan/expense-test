package com.example.expense.model;

import java.util.Arrays;

public class Expenses
{

    private int expense_id;
    private String expense_type;
    private double expense_limit;
     private byte[] expense_phto;

    public Expenses() {
    }

    public Expenses(String expense_type, double expense_limit, byte[] expense_phto) {
        this.expense_type = expense_type;
        this.expense_limit = expense_limit;
        this.expense_phto = expense_phto;
    }

    public Expenses(int expense_id, String expense_type, double expense_limit, byte[] expense_phto) {
        this.expense_id = expense_id;
        this.expense_type = expense_type;
        this.expense_limit = expense_limit;
        this.expense_phto = expense_phto;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public double getExpense_limit() {
        return expense_limit;
    }

    public void setExpense_limit(double expense_limit) {
        this.expense_limit = expense_limit;
    }

    public byte[] getExpense_phto() {
        return expense_phto;
    }

    public void setExpense_phto(byte[] expense_phto) {
        this.expense_phto = expense_phto;
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "expense_id=" + expense_id +
                ", expense_type='" + expense_type + '\'' +
                ", expense_limit=" + expense_limit +
                ", expense_phto=" + Arrays.toString(expense_phto) +
                '}';
    }
}
