package com.example.foradmin.fragment;

import android.app.Fragment;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.model.chitiethoadon;
import com.example.foradmin.model.doanhthu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_doanhthu extends Fragment {
    BarChart barChart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> labelsName;
    ArrayList<doanhthu> doanhthuArrayList = new ArrayList<>();
    ArrayList<String> choices;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanhthu,container,false);
        barChart = view.findViewById(R.id.Barchart);
        spinner = view.findViewById(R.id.spinnerThongKe);
        choices = new ArrayList<>();
        choices.add(0,"7 ngày gần nhất");
        choices.add(1,"Trong năm nay");
        choices.add(2,"Các năm qua");
        arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,choices);
        spinner.setAdapter(arrayAdapter);

        choices = new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)

                {
                    case 0:
                        String link1 = APIUtils.Base_Url + "thongkedoanhthu7ngay.php";
                        getdoanhthu(link1,"Doanh thu trong 7 ngày gần đây","Ngày",0);
                        break;
                    case 1:
                        String link2 = APIUtils.Base_Url + "getdoanhthu.php";
                        getdoanhthu(link2,"Doanh thu các tháng trong năm nay","Tháng",0);
                        break;
                    case 2:
                        String link3 = APIUtils.Base_Url + "thongkedoanhthunam.php";
                        getdoanhthu(link3,"Doanh thu các năm qua","Năm",0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                      String link1 = APIUtils.Base_Url + "thongkedoanhthu7ngay.php";
                      getdoanhthu(link1,"Doanh thu trong 7 ngày gần đây","Ngày",0);
            }
        });

        return view;
    }

    private void getdoanhthu(String link, String label, String des, int Rola) {
        doanhthuArrayList = new ArrayList<>();
        barEntries = new ArrayList<>();
        labelsName = new ArrayList<>();
        doanhthuArrayList.clear();
        barEntries.clear();
        labelsName.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test",response);
                int tien;
                String thang;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            thang  = jsonObject.getString("thang");
                            tien = jsonObject.getInt("doanhthu");
                            doanhthu DT = new doanhthu(thang,tien);
                            doanhthuArrayList.add(i,DT);
                        }

                        for (int i =0;i<doanhthuArrayList.size();i++){
                            String month = doanhthuArrayList.get(i).getThang();
                            int sales = doanhthuArrayList.get(i).getDoanhthu();

                            barEntries.add(new BarEntry(i,sales));
                            labelsName.add(""+month);
                        }
                        BarDataSet barDataSet = new BarDataSet(barEntries,label);
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        Description description = new Description();
                        description.setText(des);
                        barChart.setDescription(description);
                        BarData barData = new BarData(barDataSet);
                        barChart.setData(barData);
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setLabelRotationAngle(Rola);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setLabelCount(labelsName.size());
                        barChart.animateY(1000);
                        barChart.invalidate();
                    } catch (JSONException e) {

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        Log.d("doanhthu",doanhthuArrayList.size()+"");

    }
}
