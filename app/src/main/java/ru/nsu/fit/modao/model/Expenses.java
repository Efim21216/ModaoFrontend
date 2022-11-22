package ru.nsu.fit.modao.model;

public class Expenses {
    double expense;
    String info;
    String shortInfo;
    Currency currency;

    public Expenses(double expense, String info, String infoShort, Currency currency) {
        this.expense = expense;
        this.info = info;
        this.shortInfo = infoShort;
        this.currency = currency;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
