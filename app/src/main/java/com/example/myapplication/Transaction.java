package com.example.myapplication;

import java.util.Calendar;
import java.util.Objects;

public class Transaction implements Comparable<Transaction>{
    private double value;
    private Calendar date;

    public Transaction(double value, Calendar date)
    {
        check(value);
        this.value = value;
        this.date = date;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        check(value);
        this.value = value;
    }

    public Calendar getDate()
    {
        return date;
    }

    public void changeDate(Calendar newDate)
    {
        date = newDate;
    }

    private void check(double v)
    {
        if (v < 0) throw new IllegalArgumentException("Expense must be positive");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.value, value) == 0; //&& Objects.equals(date, that.date)
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, date);
    }

    @Override
    public int compareTo(Transaction o)
    {
        return (int) (o.value - value);
    }
}
