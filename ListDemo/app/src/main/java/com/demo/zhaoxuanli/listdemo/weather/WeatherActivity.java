package com.demo.zhaoxuanli.listdemo.weather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.default_widget.MapItem;
import com.demo.zhaoxuanli.listdemo.tool.ToolBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    private static final String FILENAME = "weather";

    private static final String url1 = "http://www.weather.com.cn/data/sk/";
    private static final String url2 = "http://www.weather.com.cn/data/cityinfo/";

    private static final int PLEASE_1 = 1;
    private static final int PLEASE_2 = 2;
    private static final int ERROR = 3;

    private EditText cityEdit;
    private Button enterBut;
    private Button refreshBut;

    private MapItem cityText,timeText,tempText,day_tempText,WDText,SDText,weatherText;

    private LinearLayout weatherLayout;

    /**当前城市code**/
    private String nowCityCode ="";

    /**用来接收线程返回的数据。
     * 当线程和handler都在activity内部，那么还有必要把数据放到message里，之后再handler中取出来么？
     * 如果这样做，多了存取步骤，不是更费时间吗**/
    private String weatherData;
    private String weatherData2;

    private JSONObject weatherJson2;
    public Handler mainHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();  //控件初始化
        initEvent();    //事件监听


        this.mainHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PLEASE_1:
                        //JSON处理数据
                        try {
                            refreshView(PLEASE_1);
                        } catch (Exception e) {
                            System.out.println("JSON解析错误");
                            e.printStackTrace();
                        }
                        break;
                    case PLEASE_2:
                        try{
                            refreshView(PLEASE_2);
                        } catch (Exception e) {
                            System.out.println("JSON解析错误");
                            e.printStackTrace();
                        }
                        break;
                    case ERROR:
                        System.out.println("JSON解析错误");
                        break;
                    default:
                        break;
                }
            }
        };

    }

    /**
     * 根据返回的Json数据刷新页面，并切换背景图片
     * @param flag
     * @throws Exception
     */
    private void refreshView(int flag) throws  Exception{
        JSONObject weatherJson;
        if(flag == PLEASE_1){
            weatherJson = new JSONObject(weatherData).getJSONObject("weatherinfo");
            cityText.setValueText(weatherJson.getString("city"));
            timeText.setValueText(weatherJson.getString("time"));
            tempText.setValueText(weatherJson.getString("temp") + "℃ ");
            WDText.setValueText(weatherJson.getString("WD") + " 、" + weatherJson.getString("WS"));
            SDText.setValueText(weatherJson.getString("SD"));
        }else{
            weatherJson = new JSONObject(weatherData2).getJSONObject("weatherinfo");
            String weather =weatherJson.getString("weather") ;
            weatherText.setValueText(weather);
            day_tempText.setValueText(weatherJson2.getString("temp1")+"℃ ~"+weatherJson2.getString("temp2")+"℃ ");
            weatherLayout.setBackgroundResource(background(weather));
        }
    }


    /*************************控件初始化*****************************/
    private void initView(){
        weatherLayout = (LinearLayout)findViewById(R.id.weatherLayout);
        enterBut = (Button)findViewById(R.id.enterBut);
        refreshBut = (Button)findViewById(R.id.refreshBut);
        cityEdit = (EditText)findViewById(R.id.cityEdit);
        cityText = (MapItem)findViewById(R.id.cityText);
        timeText = (MapItem)findViewById(R.id.timeText);
        weatherText = (MapItem)findViewById(R.id.weatherText);
        tempText = (MapItem)findViewById(R.id.tempText);
        day_tempText = (MapItem)findViewById(R.id.day_tempText);
        WDText = (MapItem)findViewById(R.id.WDText);
        SDText = (MapItem)findViewById(R.id.SDText);
    }
    /**************************事件监听**************************/
    private void initEvent(){
        enterBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {    //确定按钮
                String city = cityEdit.getText().toString();
                if (city != null && !city.equals("")) {
                    String code = CityDatas.findCityCode(city);
                    if (!code.equals("")) {
                        nowCityCode = code;
                        new NetWorkThread(code, PLEASE_1).start();
                        new NetWorkThread(code, PLEASE_2).start();
                        saveCode(code); //保存到文件
                    } else {
                        Toast.makeText(WeatherActivity.this, "你输入的城市名称有误，或数据库暂未储存您的城市",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WeatherActivity.this, "请输入城市名称",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {    //刷新按钮
                if(nowCityCode.equals(""))
                    nowCityCode = readCode();
                if(nowCityCode!=null && !nowCityCode.equals("")){
                    new NetWorkThread(nowCityCode,PLEASE_1).start();
                    new NetWorkThread(nowCityCode,PLEASE_2).start();
                }else{
                    Toast.makeText(WeatherActivity.this, "请先设置默认城市",	Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 网络线程类
     */
    class NetWorkThread extends Thread{
        String urlString;
        int flag;
        public NetWorkThread(String cityCode,int flag){
            this.flag = flag;
            if(flag == PLEASE_1)
                urlString = url1 + cityCode+ ".html";
            else
                urlString = url2 + cityCode+ ".html";
        }
        public void run(){
            try{
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                InputStream inStream = conn.getInputStream();// 通过输入流获取html数据
                byte[] data = readInputStream(inStream);// 得到html的二进制数据
                weatherData = new String(data);
                Message msg =WeatherActivity.this.mainHandler.obtainMessage(flag);
                WeatherActivity.this.mainHandler.sendMessage(msg);
            }catch (Exception e) {
                System.out.println("访问出错，可能是网络原因，给予提示");
                Message msg =WeatherActivity.this.mainHandler.obtainMessage(ERROR);
                WeatherActivity.this.mainHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 解析HTML的流数据，转为二进制数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public byte[] readInputStream(InputStream inStream) throws Exception{
        //此类实现了一个输出流，其中的数据被写入一个 byte 数组
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 字节数组
        byte[] buffer = new byte[1024];
        int len = 0;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区数组buffer 中
        while ((len = inStream.read(buffer)) != -1) {
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此输出流
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        //toByteArray()创建一个新分配的 byte 数组。
        return outStream.toByteArray();
    }

    /**
     * 根据天气状况，返回对应背景图片id
     * @param str
     * @return
     */
    private int background(String str){
        if(str.indexOf("晴")!=-1){
            return R.drawable.weather_qing;
        }else if(str.indexOf("云")!=-1){
            return R.drawable.weather_duoyun;
        }else if(str.indexOf("雨")!=-1){
            return R.drawable.weather_yu;
        }else if(str.indexOf("风")!=-1){
            return R.drawable.weather_feng;
        }else if(str.indexOf("阴")!=-1){
            return R.drawable.weather_ying;
        }else{
            return R.drawable.weather_qing;
        }
    }

    /**
     * 保存默认城市code到本地
     * @param code
     */
    private void saveCode(String code){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= getSharedPreferences(FILENAME,
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("code",code);
        //提交当前数据
        editor.commit();
    }

    /**
     * 从本地读取默认城市code
     * @return
     */
    private String readCode(){
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences(FILENAME,
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("name", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
