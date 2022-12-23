package com.example.myapplication;


import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;


public final class ExpensesPieChart implements Iterable<Category>{
    public PieChart pieChart;
    public List<Entrygory> entrygories = new ArrayList<>();

    public ExpensesPieChart(PieChart pieChart)
    {
        this.pieChart = pieChart;
        calcCenterText();
        checkZeroValues();
    }

    public void calcCenterText()
    {
        double sum = fullSum();
        if (sum == 0)
        {
            pieChart.setCenterText("You have no expenses yet");
            return;
        }
        DecimalFormat df = new DecimalFormat("0.##");
        String s = df.format(sum);
        pieChart.setCenterText("$" + s);
    }

    public void addCategories(Category... categories)
    {
        for (Category category : categories)
        {
            addCategory(category);
        }
    }

    public void addCategory(Category category)
    {
        PieEntry p = new PieEntry((float) category.getSum(), category.getName());
        entrygories.add(new Entrygory(category, p));
        pieChart.getData().getDataSet().addEntry(p);
        checkZeroValues();
        calculateValues();
        calcCenterText();
    }

    public void removeCategory(Category category)
    {
        for (Entrygory e : entrygories)
        {
            if (e.category.equals(category))
            {
                entrygories.remove(e);
                break;
            }
        }
        calculateValues();
    }

    public void setZeroValues()
    {
        for (Entrygory entrygory : entrygories)
        {
            entrygory.category.getTransactions().clear();
        }
        checkZeroValues();
        calcCenterText();
    }

    public void addTransaction(int indexOfCategory, Transaction transaction)
    {
        entrygories.get(indexOfCategory).category.addTransaction(transaction);
        checkZeroValues();
        calculateValues();
    }

    public void addTransaction(Category category, Transaction transaction)
    {
        for (Entrygory e : entrygories)
        {
            if (e.category.equals(category))
            {
                e.category.addTransaction(transaction);
                System.out.println(transaction);
                System.out.println(112);
                break;
            }
        }
        calculateValues();
        checkZeroValues();
    }

    public double sumOfCategory(int indexOfCategory)
    {
        return entrygories.get(indexOfCategory).category.getSum();
    }

    public double fullSum()
    {
        double sum = 0;
        for (Entrygory entrygory : entrygories)
        {
            sum += entrygory.category.getSum();
        }
        return sum;
    }

    public IPieDataSet getDataSet()
    {
        return pieChart.getData().getDataSet();
    }

    public void calculateValues()
    {
        for (int i=0; i<entrygories.size(); i++)
        {
            entrygories.get(i).pieEntry.setY((float) entrygories.get(i).category.getSum());
            getDataSet().getEntryForIndex(i).setY((float) entrygories.get(i).category.getSum());
            System.out.println(getDataSet().getEntryForIndex(i).getValue());
        }
    }

    private void checkZeroValues()
    {
        for (Entrygory entrygory : entrygories)
        {
            if (entrygory.category.getSum() == 0)
            {
                entrygory.pieEntry.setLabel("");
            }
        }
    }

    private void checkNonZeroValues()
    {
        for(Entrygory entrygory : entrygories)
        {
            if (entrygory.category.getSum() > 0 && entrygory.pieEntry.getLabel().equals(""))
                entrygory.pieEntry.setLabel(entrygory.category.getName());
        }
    }

    @NonNull
    @Override
    public Iterator<Category> iterator() {
        return new Iterator<Category>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < entrygories.size();
            }

            @Override
            public Category next() {
                return entrygories.get(index++).category;
            }
        };
    }
}
