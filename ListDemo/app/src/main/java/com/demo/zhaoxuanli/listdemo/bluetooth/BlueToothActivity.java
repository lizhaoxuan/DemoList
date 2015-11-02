package com.demo.zhaoxuanli.listdemo.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.recycler_view.RecycleViewActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BlueToothActivity extends AppCompatActivity {
    private ListView deviceList;
    private Button aganiBut;
    private Button cancelBut;
    private BluetoothAdapter blueAdapter;
    private ProgressDialog pd;
    private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter listAdapter;

    private OutputStream btOutputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        initView();
        initEvent();
    }


    private void initView(){
        aganiBut = (Button)findViewById(R.id.aganiBut);
        cancelBut = (Button)findViewById(R.id.cancelBut);
        deviceList = (ListView)findViewById(R.id.deviceList);
        listAdapter = new SimpleAdapter(this,
                list, R.layout.item_blue_tooth,
                new String[]{"bt_ssid","bt_mac"} ,
                new int[]{R.id.ssidText,R.id.macText});
        deviceList.setAdapter(listAdapter);

        blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(blueAdapter != null){
            System.out.println("有蓝牙设备");
            if(!blueAdapter.isEnabled()){
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
            } else {
                list.clear();
                listAdapter.notifyDataSetChanged();
                // 设置广播信息过滤
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
                intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                // 注册广播接收器，接收并处理搜索结果
                registerReceiver(receiver, intentFilter);
                // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
                blueAdapter.startDiscovery();
            }
            Toast.makeText(BlueToothActivity.this, "蓝牙配对设备开始搜寻",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent(){

        aganiBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //重新搜索
                list.clear();
                listAdapter.notifyDataSetChanged();
                // 设置广播信息过滤
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
                intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                // 注册广播接收器，接收并处理搜索结果
                registerReceiver(receiver, intentFilter);
                // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
                blueAdapter.startDiscovery();
            }
        });
        cancelBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //取消返回
                Intent intent = new Intent();
                intent.setClass(BlueToothActivity.this, RecycleViewActivity.class);
                startActivity(intent);
                BlueToothActivity.this.finish();//从栈中移除
                BlueToothActivity.this.onDestroy();//销毁
            }
        });
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                blueAdapter.cancelDiscovery();
                //	String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
                // UUID uuid = UUID.fromString(SPP_UUID);
                HashMap<String,String> map=(HashMap<String,String>)arg0.getItemAtPosition(arg2);
                String bt_ssid=map.get("bt_ssid");
                String bt_mac =map.get("bt_mac");
                System.out.println("蓝牙设备："+bt_ssid+"地址值为  :  "+bt_mac);



                BluetoothSocket btSocket = null;
                BluetoothDevice btDevice;
                btDevice = blueAdapter.getRemoteDevice(bt_mac);

                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID

                Method m;
                try {
                    m = btDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                    btSocket=(BluetoothSocket) m.invoke(btDevice,Integer.valueOf(1));
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
	        	try {
					btSocket = btDevice.createInsecureRfcommSocketToServiceRecord(uuid);
				} catch (IOException e) {
					System.out.println("获取设备失败");
					e.printStackTrace();
				}
                try {
                    btSocket.connect();
                    btOutputStream = btSocket.getOutputStream();
                    Toast.makeText(BlueToothActivity.this, "设备连接成功！",
                            Toast.LENGTH_SHORT).show();
                    /*连接以成功，接下来可以调用sendMessage发送消息*/

                } catch (IOException e) {
                    //PublicData.bule_connect=false;
                    Toast.makeText(BlueToothActivity.this, "设备连接失败！",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }
        });


    }

    public boolean onKeyDown(int KeyCode, KeyEvent event){
        //返回按钮监听
        if(KeyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            BlueToothActivity.this.finish();//从栈中移除
            BlueToothActivity.this.onDestroy();//销毁

        }

        return false;
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                System.out.println(device.getName());
                //创建HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("bt_ssid", device.getName());
                map.put("bt_mac", device.getAddress());
                list.add(map);
                listAdapter.notifyDataSetChanged();
            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                //	搜寻结束
                Toast.makeText(BlueToothActivity.this, "蓝牙配对设备搜寻结束",
                        Toast.LENGTH_SHORT).show();

                System.out.println("查询结束");
            }
        }
    };


    //通过蓝牙发送信息
    public void sendMessage(String str,Context context){

        byte [] b = null;
        try {
            b = str.getBytes("GBK");
            btOutputStream.write(b);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(context, "转字节流出现异常", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "蓝牙发送出现异常，请检查蓝牙设备", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        System.out.println("发送成功："+str);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blue_tooth, menu);
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
