package com.example.jurgenhaan.project4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream inputStream = getResources().openRawResource(R.raw.fietstrommels_maart_2013);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> FietsList = csvFile.read();
        MyListAdapter adapter=new MyListAdapter(this, R.layout.listrow,R.id.txtid, FietsList);

        int Centrum = new ListIterator().iterate(FietsList,"Centrum");
        int Noord = new ListIterator().iterate(FietsList,"Noord");
        int Delfshaven = new ListIterator().iterate(FietsList,"Delfshaven");
        int Feijenoord = new ListIterator().iterate(FietsList,"Feijenoord");
        int Kralingen = new ListIterator().iterate(FietsList,"Kralingen/Crooswijk");
        int Charlois = new ListIterator().iterate(FietsList,"Charlois");
        int Overschie = new ListIterator().iterate(FietsList,"Overschie");
        int Hillegersberg = new ListIterator().iterate(FietsList,"Hillegersberg");
        int Ijsselmonde = new ListIterator().iterate(FietsList,"Ijsselmonde");
        int Hoogvliet = new ListIterator().iterate(FietsList,"Hoogvliet");
        int Omoord = new ListIterator().iterate(FietsList,"Omoord");
        int Pernis = new ListIterator().iterate(FietsList,"Pernis");
        int West = new ListIterator().iterate(FietsList,"West");


        barChart = (BarChart) findViewById(R.id.bargraph);

        createRandomBarGraph("2016/05/05", "2016/06/01");

//        Void chart = new Chart().MakeChart(Centrum,Charlois,Delfshaven,Feijenoord,Hillegersberg,Hoogvliet,Ijsselmonde,Kralingen,Noord,Omoord,Overschie,Pernis,West);
    }

    private class CSVFile {
        InputStream inputStream;

        public CSVFile(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public List<String[]> read() {
            //
            List<String[]> resultList = new ArrayList<String[]>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    resultList.add(row);
                }
            } catch (IOException e) {
                Log.e("Main", e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("Main", e.getMessage());
                }
            }

            return resultList;
        }

    }
    public class ListIterator {
        int current = -1;
        int amount = 0;
        String[] Raphael;
        ArrayList newList = new ArrayList();
        public int iterate(List<String[]> Fietslist,String plaats){
            Iterator itr = Fietslist.iterator() ;
            while (itr.hasNext() ){
                current += 1;
                Raphael = Fietslist.get(current);
                newList.add(Raphael[7]);
                itr.next();
                Log.d("Entries"+ current,"Thingie");

            }
            for (int i=0;i < newList.size();i++)
                if (newList.get(i).equals(plaats)){
                    amount += 1;
                }


            return amount ;

        }
    }



//    public class Chart{
//
//        public Void MakeChart(int Centrum,int Charlois,int Delfshaven,int Feijenoord,int Hillegersberg,int Hoogvliet,int Ijsselmonde,int Kralingen,int Noord,int Omoord,int Overschie,int Pernis, int West) {
//
//            ArrayList list =  new ArrayList();
//            list.add(Centrum);
//            list.add(Charlois);
//            if (list.size() == 0){
//                list.add(Centrum);
//                list.add(Charlois);
//                list.add(Delfshaven);
//                list.add(Feijenoord);
//                list.add(Hillegersberg);
//            }
//            if (Hoogvliet > list[1])
//
//            Context context = getApplicationContext();
//            ArrayList<BarEntry> entries = new ArrayList<>();
//            entries.add(new BarEntry(Centrum,0));
//            entries.add(new BarEntry(Charlois,1));
//            entries.add(new BarEntry(Delfshaven,2));
//            BarDataSet dataset = new BarDataSet(entries,"Number of bike containers");
//            ArrayList<String> Labels = new ArrayList<String>();
//            Labels.add("Centrum");
//            Labels.add("Charlois");
//            Labels.add("Delfshaven");
//            BarChart chart = new BarChart(context);
//            setContentView(chart);
//            BarData data = new BarData(Labels, dataset);
//            chart.setData(data);
//            return null;
//        }
//    }



    public void createRandomBarGraph(String Date1, String Date2){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1,mDate2);

            barEntries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random();
            for(int j = 0; j< dates.size();j++){
                max = 100f;
                value = random.nextFloat()*max;
                barEntries.add(new BarEntry(value,j));
            }

        }catch(ParseException e){
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
        BarData barData = new BarData(dates,barDataSet);
        barChart.setData(barData);
        barChart.setDescription("My First Bar Graph!");

    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<String>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }

    public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                +cld.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate =  new SimpleDateFormat("yyy/MM/dd").format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return curDate;
    }

}
