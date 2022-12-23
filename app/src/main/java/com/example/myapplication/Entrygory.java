package com.example.myapplication;

import com.github.mikephil.charting.data.PieEntry;

public class Entrygory {
    public PieEntry pieEntry;
    public Category category;

    public Entrygory(Category category, PieEntry pieEntry)
    {
        this.category = category;
        this.pieEntry = pieEntry;
    }
}
