package tw.com.watsonfan.studentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BusStation extends AppCompatActivity {

    private String url="http://ibus.tbkc.gov.tw/xmlbus/StaticData/GetStop.xml?routeIds=";
    private OkHttpClient client;
    int goBack;
    ArrayList<BusItem> listData = null;

    private ListView stationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_station);

        stationList = findViewById(R.id.stationList);

        Bundle bundle = getIntent().getExtras();
        String Rid = bundle.getString("RID");
        goBack = bundle.getInt("goBack");
        client = new OkHttpClient();
        url  = url + Rid;
        parseXML(url,goBack);

    }

    private void parseXML(String url,final int goBack){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                final String busdata = response.body().string();
                final InputStream stream = new ByteArrayInputStream(busdata.getBytes(StandardCharsets.UTF_8.name()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listData = parse(stream);

                        stationList.setAdapter(new StationAdapter(BusStation.this,listData));

                    }
                });



            }
        });
    }




    public ArrayList<BusItem> parse(InputStream input){

        String tagName = null;
        ArrayList<BusItem> arrayList = new ArrayList<>();
        int find = 0;
        try{

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //??????XML ?????????
            XmlPullParser parser = factory.newPullParser();

            //???XML????????????
            parser.setInput(new InputStreamReader(input));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){

                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("Stop") & find == 0){

                            if(goBack == Integer.parseInt(parser.getAttributeValue(null,"GoBack"))){
                                find++;
                                BusItem  busItem = new BusItem();
                                busItem.setStation(parser.getAttributeValue(null,"nameZh"));
                                arrayList.add(busItem);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("Stop")){
                            find = 0;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                }
                eventType = parser.next();   //?????????????????????

            }

            return arrayList;



        }catch(Exception e){
            e.printStackTrace();
        }

        return arrayList;

    }




}