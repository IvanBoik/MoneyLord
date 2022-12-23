package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.PointerIcon;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Stack;

public class ExpensesPage extends AppCompatActivity {
    public ExpensesPieChart expensesPieChart;
    private final List<Category> categories = new ArrayList<>();
    private AlertDialog dialog;
    private TextView resultField;
    private Category categoryNow;
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_page);
        PieChart pieChart = findViewById(R.id.pie_chart);
        setupPieChart(pieChart);
        startLoadPieChartData(pieChart);
        expensesPieChart = new ExpensesPieChart(pieChart);

        expensesPieChart.addCategories(
                new Category("Lunch & Dinner", R.drawable.ic_burger, new Transaction(100.2, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Medical Allowances", R.drawable.ic_burger, new Transaction(150, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Transport", R.drawable.ic_house__2_, new Transaction(200, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Heath", R.drawable.ic_house__2_, new Transaction(500, new GregorianCalendar(2022, Calendar.NOVEMBER, 26)))
        );
        setInitialData(
                new Category("Lunch & Dinner", R.drawable.ic_burger, new Transaction(100.2, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Medical Allowances", R.drawable.ic_burger, new Transaction(150, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Transport", R.drawable.ic_house__2_, new Transaction(200, new GregorianCalendar(2022, Calendar.NOVEMBER, 26))),
                new Category("Heath", R.drawable.ic_house__2_, new Transaction(500, new GregorianCalendar(2022, Calendar.NOVEMBER, 26)))
        );
        recyclerView = findViewById(R.id.expenses_category_list);
        adapter = new CategoryAdapter(this, categories, R.layout.category_list_item, R.id.categoryImage, R.id.categoryTitle, R.id.categoryValue, R.id.categoryTransactions);
        recyclerView.setAdapter(adapter);
    }

    public void setInitialData(Category... categories)
    {
        this.categories.addAll(Arrays.asList(categories));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupPieChart(PieChart pieChart)
    {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(false);
        pieChart.setEntryLabelTextSize(16);
        pieChart.setEntryLabelColor(Color.BLACK);
        Typeface centerTf = Typeface.createFromAsset(getAssets(), "fonts/DMSans-Bold.ttf");
        pieChart.setCenterTextTypeface(centerTf);

        Typeface labelTf = Typeface.createFromAsset(getAssets(), "fonts/DMSans-Regular.ttf");
        pieChart.setEntryLabelTypeface(labelTf);

        pieChart.setCenterTextSize(24);
        pieChart.setNoDataText("You have no expenses yet");
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void startLoadPieChartData(PieChart pieChart)
    {
        List<PieEntry> entries = new ArrayList<>();

        List<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) colors.add(color);

        for (int color : ColorTemplate.VORDIPLOM_COLORS) colors.add(color);

        PieDataSet dataSet = new PieDataSet(entries, "Expenses categories");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    public void transactionOfCategory(Category category)
    {
        categoryNow = category;
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        ConstraintLayout view = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog, null);
        TextView title = view.findViewById(R.id.nameOfCategory);
        TextView date = view.findViewById(R.id.date);
        resultField = view.findViewById(R.id.inputSum);
        date.setText("TODAY, " + (new GregorianCalendar().get(Calendar.MONTH)+1) + "." + new GregorianCalendar().get(Calendar.DAY_OF_MONTH) + "." + new GregorianCalendar().get(Calendar.YEAR));
        title.setText(category.getName());

        adb.setView(view);
        dialog = adb.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    public void onNumberClick(View view){

        Button button = (Button)view;
        if (resultField.getText().toString().equals("$0") && !button.getText().toString().equals("."))
        {
            resultField.setText("$");
        }

        boolean isExpression = false;
        String res = resultField.getText() + "";

        for (int i=0; i<res.length(); i++)
        {
            if (res.charAt(i) == '-' || res.charAt(i) == '+' || res.charAt(i) == '*' || res.charAt(i) == '/')
            {
                isExpression = true;
                break;
            }
        }

        if (button.getText().toString().equals("0"))
        {
            if (res.equals("$"))
            {
                resultField.setText("$0");
                return;
            }
        }
        resultField.append(button.getText() + "");
    }

    @SuppressLint("SetTextI18n")
    public void onOperationClick(View view){

        Button button = (Button)view;
        String op = button.getText() + "";
        String number = resultField.getText() + "";

        if (!number.equals("$0"))
        {
            switch (op){
                case "+":
                    resultField.append("+");
                    break;
                case "-":
                    resultField.append("-");
                    break;
                case "*":
                    resultField.append("*");
                    break;
                case "/":
                    resultField.append("/");
                    break;
                case "X":
                    String s = resultField.getText() + "";
                    String s1 = s.substring(0, s.length()-1);
                    if (!s1.equals("$"))
                        resultField.setText(s1);
                    else
                        resultField.setText("$0");
                    break;
                case "OK":
                    boolean isExpression = false;
                    String res = resultField.getText() + "";

                    for (int i=0; i<res.length(); i++)
                    {
                        if (res.charAt(i) == '-' || res.charAt(i) == '+' || res.charAt(i) == '*' || res.charAt(i) == '/')
                        {
                            isExpression = true;
                            break;
                        }
                    }

                    String without$ = res.replace("$", "");
                    double evalRes = eval(without$);
                    String evalResString = new DecimalFormat("0.##").format(evalRes);
                    if (isExpression)
                    {
                        resultField.setText("$" + evalResString);
                    }
                    else
                    {
                        Transaction t = new Transaction(evalRes, new GregorianCalendar(2022, 11, 23));
                        categoryNow.addTransaction(t);
                        dialog.dismiss();
                        adapter = new CategoryAdapter(this, categories, R.layout.category_list_item, R.id.categoryImage, R.id.categoryTitle, R.id.categoryValue, R.id.categoryTransactions);
                        recyclerView.setAdapter(adapter);

                        expensesPieChart.addTransaction(categoryNow, t);
                    }
                    break;
            }
        }
    }

    private double eval(String input)
    {
        Stack<Integer> operator  = new Stack<>();
        Stack<Double> value = new Stack<>();

        Stack<Integer> tmpOp  = new Stack<>();
        Stack<Double> tmpVal = new Stack<>();

        input = "0" + input;
        input = input.replaceAll("-","+-");

        String temp = "";
        for (int i = 0;i < input.length();i++){
            char ch = input.charAt(i);
            if (ch == '-')
                temp = "-" + temp;
            else if (ch != '+' &&  ch != '*' && ch != '/')
                temp = temp + ch;
            else{
                value.push(Double.parseDouble(temp));
                operator.push((int)ch);
                temp = "";
            }
        }
        value.push(Double.parseDouble(temp));

        char operators[] = {'/','*','+'};

        /* Evaluation of expression */
        for (int i = 0; i < 3; i++){
            boolean it = false;
            while (!operator.isEmpty()){
                int optr = operator.pop();
                double v1 = value.pop();
                double v2 = value.pop();

                if (optr == operators[i]){
                    if (i == 0){
                        tmpVal.push(v2 / v1);
                        it = true;
                        break;
                    }
                    else if (i == 1){
                        tmpVal.push(v2 * v1);
                        it = true;
                        break;
                    }
                    else if (i == 2){
                        tmpVal.push(v2 + v1);
                        it = true;
                        break;
                    }
                }
                else{
                    tmpVal.push(v1);
                    value.push(v2);
                    tmpOp.push(optr);
                }
            }
            while (!tmpVal.isEmpty())
                value.push(tmpVal.pop());
            while (!tmpOp.isEmpty())
                operator.push(tmpOp.pop());
            if (it)
                i--;
        }
        return value.pop();
    }

    public void backToTheMainAgain(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
