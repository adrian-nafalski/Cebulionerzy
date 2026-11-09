package com.example.cebulionerzy.gameFragment.lifebuoyFragments;

import static com.example.cebulionerzy.MainActivity.listOfQuestions;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cebulionerzy.ItemVievModel;
import com.example.cebulionerzy.QuizModelClass;
import com.example.cebulionerzy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class NationFragment extends DialogFragment {

    private TextView title;
    private BarChart barChart;
    private ArrayList barDataList;
    final private List<String> xLabels = new ArrayList<>();
    private QuizModelClass quizModelClass;
    private float ansA, ansB, ansC, ansD;
    private String correct_Ans = "";

    public NationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.fragment_nation, container, false);

        barChart = v.findViewById(R.id.barChart);
        title = v.findViewById(R.id.title);

        return v;
    }

    private void getData(float ansA, float ansB, float ansC, float ansD) {
        // podpisy danych
        xLabels.add("A");
        xLabels.add("B");
        xLabels.add("C");
        xLabels.add("D");

        barDataList = new ArrayList();

        // dane do wykresów
        barDataList.add(new BarEntry(1,ansA));
        barDataList.add(new BarEntry(2,ansB));
        barDataList.add(new BarEntry(3,ansC));
        barDataList.add(new BarEntry(4,ansD));
    }

    private void drawBarChart() {
/*
        float groupSpace = 0.10f; // do ustalenia odstępów
        float barSpace = 0.02f; // do ustalenia odstępów niędzy wykresami
*/
        float barWidth = 0.6f; // do ustalenia szerokości wykresów

        BarDataSet barDataSet = new BarDataSet(barDataList, "Polacy");
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(barWidth);

        barChart.setData(barData);
        barChart.animateY(1000); // animacja wyborów
        barChart.setTouchEnabled(false);
        barChart.invalidate();
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setExtraBottomOffset(2);

        barDataSet.setColors(Color.BLUE);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);

        // formatowanie danych na wykresie oby dodać znak procenta na końcu
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value) + "%";
            }
        });

        // Ustawienia etykiet lewa - prawa i rysowania wykresu na osi Y
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Ustawienia etykiet góra - dół i rysowania wykresu na osi X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setLabelCount(xLabels.size());
        xAxis.setTextSize(15f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0);
    }

    /**
     * Funkcja obsługująca mechanizm poprawnego głosowania narodu (widowni).
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemVievModel itemVievModel = new ViewModelProvider(requireActivity()).get(ItemVievModel.class);
        itemVievModel.getSelectedItem().observe(getActivity(), item -> {
            try {
                quizModelClass = listOfQuestions.get(item);
                correct_Ans = quizModelClass.getAns();

                if(quizModelClass.getoA().equals(correct_Ans)) {
                    correct_Ans = "A";
                }
                if(quizModelClass.getoB().equals(correct_Ans)) {
                    correct_Ans = "B";
                }
                if(quizModelClass.getoC().equals(correct_Ans)) {
                    correct_Ans = "C";
                }
                if(quizModelClass.getoD().equals(correct_Ans)) {
                    correct_Ans = "D";
                }

                // wyniki głosowania - zawsze najwięcej głosów na poprawną odpowiedź
                while (true) {
                    generateVote();

                    if(correct_Ans.equals("A")) {
                        if((ansA > ansB) && (ansA > ansC) && (ansA > ansD)) {
                            return;
                        }
                    }
                    if(correct_Ans.equals("B")) {
                        if((ansB > ansA) && (ansB > ansC) && (ansB > ansD)) {
                            return;
                        }
                    }
                    if(correct_Ans.equals("C")) {
                        if((ansC > ansB) && (ansC > ansA) && (ansC > ansD)) {
                            return;
                        }
                    }
                    if(correct_Ans.equals("D")) {
                        if((ansD > ansB) && (ansD > ansC) && (ansD > ansA)) {
                            return;
                        }
                    }
                }

            } catch (RuntimeException e) {
                e.getStackTrace();
            }
        });

        try {
            getData(0f,0f,0f,0f);
            drawBarChart();

            Handler handler = new Handler(Looper.getMainLooper());

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // trzeba wyczyścić tablice z danymi bo dzieją się dziwne rzeczy na wykresie
                    xLabels.clear();
                    barDataList.clear();

                    title.setText("Polacy zdecydowali!");

                    // formatowanie danych do dwóch miejsc po przecinku
                    BigDecimal b1 = new BigDecimal(ansA).setScale(2, RoundingMode.HALF_EVEN);
                    BigDecimal b2 = new BigDecimal(ansB).setScale(2, RoundingMode.HALF_EVEN);
                    BigDecimal b3 = new BigDecimal(ansC).setScale(2, RoundingMode.HALF_EVEN);
                    BigDecimal b4 = new BigDecimal(ansD).setScale(2, RoundingMode.HALF_EVEN);

                    // uzupełnianie danych aby wyświetlić na wykresie
                    getData((float) b1.doubleValue(),
                            (float) b2.doubleValue(),
                            (float) b3.doubleValue(),
                            (float) b4.doubleValue());

                    // rysowanie wykresu
                    drawBarChart();
                }
            }, 2000);
        } catch (RuntimeException e) {
            e.getStackTrace();
        }
    }

    /**
     * Działaja symulacja głosowania w teleturnieju
     */
    private void generateVote() {
        float n1,n2,n3;

        n1 = (float) ((Math.random() * (100f - 0f)) + 0f);
        n2 = (float) ((Math.random() * (100f - 0f)) + 0f);
        n3 = (float) ((Math.random() * (100f - 0f)) + 0f);

        float min = Math.min(n1, Math.min(n2,n3));
        float max = Math.max(n1, Math.max(n2, n3));
        float mid = n1 + n2 + n3 - max - min;

        ansA = min - 0;
        ansB = mid - min;
        ansC = max - mid;
        ansD = 100 - max;

        // Dla sprawdzenia sumy i czy wygenerowane wyniki są poprawne
//        float sum = 0f;
//
//        sum = ansA + ansB + ansC + ansD;
//        System.out.println("Generowanie liczb: " + "ansA: " + ansA + " ansB: " + ansB + " ansC: " + ansC + " ansD: " + ansD + " sum: " + sum);
    }
}