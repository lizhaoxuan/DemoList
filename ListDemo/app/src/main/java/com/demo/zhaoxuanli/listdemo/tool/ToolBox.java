package com.demo.zhaoxuanli.listdemo.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
/**
 * Created by zhaoxuan.li on 2015/10/14.
 */
public class ToolBox {



    //向文件中写入数据		   文件名，文件夹名，字符串
    public boolean writeFile (String FILENAME,String DIR,String DATA){
        DIR="Light_Cube/"+DIR;

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + DIR + File.separator + FILENAME); // 定义要操作的文件
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs(); // 创建父文件夹路径
            }
            PrintStream out = null;
            try {
                out = new PrintStream(new FileOutputStream(file));   //默认覆盖，(file,true)指不覆盖而追加
                out.println(DATA);
            } catch (Exception e) {
                e.printStackTrace();
            } finally { // 一定要关闭流
                if (out != null) {
                    out.close();
                }
            }
            return true;
        } else {
            return false;
        }
    }
    //从文件中读取数据	文件名，文件夹名
    public String  readFile (String FILENAME,String DIR){
        DIR="Light_Cube/"+DIR;
        String DATA="";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + DIR + File.separator + FILENAME); // 定义要操作的文件
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs(); // 创建父文件夹路径
                return DATA;
            }
            Scanner scan = null ;
            try {
                scan = new Scanner(new FileInputStream(file)) ;
                while(scan.hasNext()) {
                    DATA=DATA+scan.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return DATA;
            } finally { // 一定要关闭流
                if (scan != null) {
                    scan.close();
                }
            }
        } else {
            System.out.println("储存卡不存在");
        }
        return DATA;
    }
    //通过蓝牙发送信息
    public void sendMessage(String str,Context context,OutputStream btOutputStream){
        String testString=str;
        byte [] b = null;
        try {
            b = testString.getBytes("GBK");
            btOutputStream.write(b);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(context, "转字节流出现异常", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "蓝牙发送出现异常，请检查蓝牙设备", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        System.out.println(new String(b));
    }

}
