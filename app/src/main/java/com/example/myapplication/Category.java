package com.example.myapplication;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Category implements Comparable<Category>{
    private int imageResource;
    private String name;
    private final List<Transaction> transactions = new ArrayList<>();

    public Category(String name, int image, Transaction... transactions)
    {
        this.imageResource = image;
        this.name = name;
        this.transactions.addAll(Arrays.asList(transactions));
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSum() {
        double sum = 0;
        for (Transaction t : transactions) sum += t.getValue();
        return sum;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public Transaction getTransaction(int index)
    {
        return transactions.get(index);
    }

    public void addTransaction(Transaction t)
    {
        transactions.add(t);
    }

    public void setTransaction(int index, Transaction t)
    {
        transactions.set(index, t);
    }

    @Override
    public int compareTo(Category o) {
        return (int) (o.getSum() - getSum());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name); //&& Objects.equals(transactions, category.transactions)
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageResource, name, transactions);
    }

    @NonNull
    @Override
    public String toString() {
        return "Category{" +
                "imageResource=" + imageResource +
                ", name='" + name + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
