package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class DetailsPage extends AppCompatActivity {

    private LineChart chart;
    List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        makeChart();

        setInitialData(
                new Category("Lunch & Dinner", R.drawable.ic_burger, new Transaction(100.2, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Medical Allowances", R.drawable.ic_burger, new Transaction(150, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Transport", R.drawable.ic_house__2_, new Transaction(200, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Heath", R.drawable.ic_house__2_, new Transaction(500, new GregorianCalendar(2022, Calendar.NOVEMBER, 26)))
        );
        RecyclerView recyclerView = findViewById(R.id.list);
        CategoryAdapter adapter = new CategoryAdapter(this, categories, R.layout.top_category_list_item, R.id.detailsItemImage, R.id.detailsItemTitle, R.id.detailsItemValue, R.id.empty);
        recyclerView.setAdapter(adapter);
    }


    public void setInitialData(Category... categories)
    {
        this.categories.addAll(Arrays.asList(categories));
        Collections.sort(this.categories);
    }

    public void makeChart()
    {
        chart = findViewById(R.id.chart1);

        Random r = new Random();
        int[] rArr = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        int[] positiveArr = {0, 1, 2, 3, 4, 5};
        ArrayList<Entry> entriesFirst  = new ArrayList<>();
        for (int i=1; i<21; i++)
        {
            int y;
            if (i < 5) y = positiveArr[r.nextInt(6)];
            else y = rArr[r.nextInt(11)];
            entriesFirst.add(new Entry(i, i + y));
        }

        LineDataSet datasetFirst = new LineDataSet(entriesFirst , "График первый");
        datasetFirst.setColor(Color.parseColor("#7165E3"));
        datasetFirst.setFillAlpha(1000);
        datasetFirst.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datasetFirst.setDrawCircles(false);
        datasetFirst.setDrawFilled(true);
        datasetFirst.setFillColor(7431651); //#7165E3

        ArrayList<Entry> entriesSecond = new ArrayList<>();
        for (int i=1; i<31; i++)
        {
            int y;
            if (i < 5) y = positiveArr[r.nextInt(6)];
            else y = rArr[r.nextInt(11)];
            entriesSecond.add(new Entry(i, i + y));
        }

        LineDataSet datasetSecond = new LineDataSet(entriesSecond, "График второй");
        datasetSecond.setColor(Color.parseColor("#697596"));
        datasetSecond.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datasetSecond.setDrawCircles(false);
        datasetSecond.setDrawFilled(true);
        datasetSecond.setFillColor(6911382); //#697596

        datasetFirst.setDrawValues(false);
        datasetSecond.setDrawValues(false);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        leftAxis.setEnabled(false);
        rightAxis.setEnabled(false);

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(datasetFirst);
        dataSets.add(datasetSecond);

        LineData data = new LineData(dataSets);
        chart.setData(data);

        chart.invalidate();
    }

    public void backToTheMain(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}