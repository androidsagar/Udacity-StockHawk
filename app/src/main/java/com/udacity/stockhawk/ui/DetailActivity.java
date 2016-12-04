package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

   @BindView(R.id.lineChart_activity_line_graph)
    LineChart lineChart;
    @BindView(R.id.stock_name)
    TextView textStockName;
    @BindView(R.id.stockprice)
    TextView textStockPrice;



    String name = "";
    String symbol = "";
    String price = "";
    String[] historyDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIntentExtra();
        createChart();
        textStockName.setText(name);
        textStockPrice.setText(price);

    }

    private void createChart(){
        ArrayList<Entry> entrylist =new ArrayList<>();
        ArrayList<String> xList =new ArrayList<>();

        for (int i=0;i<historyDetails.length;i++){
             xList.add(historyDetails[i].split(",")[0]);
             entrylist.add(new Entry(Float.parseFloat(historyDetails[i].split(",")[1]),i));
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setLabelsToSkip(20);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15f);
        xAxis.setTextColor(Color.rgb(182,182,182));

        YAxis left = lineChart.getAxisLeft();
        left.setEnabled(true);
        left.setLabelCount(10, true);
        left.setTextColor(Color.rgb(182,182,182));

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setTextSize(16f);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(Color.rgb(25,118,210));


        LineDataSet dataSet = new LineDataSet(entrylist, symbol);
        LineData lineData = new LineData(xList, dataSet);

        lineChart.animateX(2500);
        lineChart.setData(lineData);

    }

    private void getIntentExtra(){
        Intent intent=getIntent();
        symbol=intent.getStringExtra(Contract.Quote.COLUMN_SYMBOL);
        price=intent.getStringExtra(Contract.Quote.COLUMN_PRICE);
        String history=intent.getStringExtra(Contract.Quote.COLUMN_HISTORY);
        historyDetails=history.split("\n");
        name=intent.getStringExtra(Contract.Quote.COLUMN_NAME);


    }
}
