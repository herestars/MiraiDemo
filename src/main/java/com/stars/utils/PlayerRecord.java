package com.stars.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stars.MiraiDemo;
import com.stars.entity.Record;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerRecord {
    public static final String filePath =
            new File("").getAbsolutePath() + File.separator + "DuelData" + File.separator;
    public static final String fileName = "PlayerRecord.json";
    public static Map<Long,Record> recordMap = new HashMap<>();

    public static void AddWin(Long fromQQ,String NickName) {
        recordMap.getOrDefault(fromQQ,new Record(fromQQ,NickName,Long.valueOf(0),Long.valueOf(0),Long.valueOf(0))).AddWin();
    }

    public static void AddLose(Long fromQQ,String NickName){
        recordMap.getOrDefault(fromQQ,new Record(fromQQ,NickName,Long.valueOf(0),Long.valueOf(0),Long.valueOf(0))).AddLose();
    }

    public static void AddDraw(Long fromQQ,String NickName){
        recordMap.getOrDefault(fromQQ,new Record(fromQQ,NickName,Long.valueOf(0),Long.valueOf(0),Long.valueOf(0))).AddDraw();
    }

    public void createFile() {
        File file = new File(filePath + fileName);

        file.getParentFile().mkdirs();

        try {
            file.createNewFile();

            // 写入默认值，防止读取后 recordMap 为null
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new GsonBuilder().serializeNulls().create();
            Record defaultRecord = new Record(Long.valueOf(-1),"defaultUser",Long.valueOf(0),Long.valueOf(0),Long.valueOf(0));
            recordMap.put((long)-1,defaultRecord);
            System.out.println(defaultRecord);
            String defaultJson = gson.toJson(recordMap);
            fos.write(defaultJson.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void loadFile(File file){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            // 从<本地存储>中<读取JSON配置文件数据>
            this.recordMap = new Gson().fromJson(reader,
                    new TypeToken<Map<Long,Record>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            // Load完毕立即close掉本地文件流, 以免资源占用.
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveFile() throws IllegalArgumentException,
            IllegalAccessException, IOException {

        // 定义要写出的本地配置文件
        File file = new File(filePath + fileName);
        FileOutputStream fos = new FileOutputStream(file);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String defaultJson = gson.toJson(recordMap);

        fos.write(defaultJson.getBytes());
        fos.flush();
        fos.close();

    }

    public void init(){
        File file = new File(filePath + fileName);
        MiraiDemo.INSTANCE.getLogger().info(file.getAbsolutePath());
        if(file.exists() == false) {
            this.createFile();
            try {
                this.saveFile();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadFile(file);

    }
}

