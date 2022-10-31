/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package temp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author lpphu
 * @param <T>
 */
public class ObjectSend<T> implements Serializable{
    public ArrayList<T> arr;
    public ArrayList<T> arrTemp;
    public String tag;
    public T object;
    public T objectTemp;
    public String alert;
    
    public ObjectSend()
    {
        
    }
    
    public ObjectSend(ArrayList<T> arr, String tag) {
        this.arr = arr;
        this.tag = tag;
    }

    public ObjectSend(ArrayList<T> arr, String tag, T object) {
        this.arr = arr;
        this.tag = tag;
        this.object = object;
    }

    public ObjectSend(ArrayList<T> arr, String tag, T object, T objectTemp) {
        this.arr = arr;
        this.tag = tag;
        this.object = object;
        this.objectTemp = objectTemp;
    }

    public ObjectSend(ArrayList<T> arr, ArrayList<T> arrTemp, String tag, T object, T objectTemp) {
        this.arr = arr;
        this.arrTemp = arrTemp;
        this.tag = tag;
        this.object = object;
        this.objectTemp = objectTemp;
    }

    public ObjectSend(ArrayList<T> arr, ArrayList<T> arrTemp, String tag, T object) {
        this.arr = arr;
        this.arrTemp = arrTemp;
        this.tag = tag;
        this.object = object;
    }

    public ObjectSend(String tag, T object, T objectTemp, String alert) {
        this.tag = tag;
        this.object = object;
        this.objectTemp = objectTemp;
        this.alert = alert;
    }

    public ObjectSend(ArrayList<T> arr, ArrayList<T> arrTemp, String tag) {
        this.arr = arr;
        this.arrTemp = arrTemp;
        this.tag = tag;
    }
    
    public static String getCurrentTime()
    {
       Date date = new Date();
       Timestamp timetamp = new Timestamp(date.getTime());
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
       return format.format(timetamp);
    }
    public static String getOnlyTime()
    {
        Date date = new Date();
       Timestamp timetamp = new Timestamp(date.getTime());
       SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); 
       return format.format(timetamp);
    }
    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
    
    public ObjectSend(String tag, T object) {
        this.tag = tag;
        this.object = object;
    }
    public ObjectSend(String tag, T object, T objectTemp) {
        this.tag = tag;
        this.object = object;
        this.objectTemp = objectTemp;
    }

    public T getObjectTemp() {
        return objectTemp;
    }

    public void setObjectTemp(T objectTemp) {
        this.objectTemp = objectTemp;
    }

    public ArrayList<T> getArrTemp() {
        return arrTemp;
    }

    public void setArrTemp(ArrayList<T> arrTemp) {
        this.arrTemp = arrTemp;
    }
    
    public ObjectSend(String tag) {
        this.tag = tag;
    }

    public ArrayList<T> getArr() {
        return arr;
    }

    public void setArr(ArrayList<T> arr) {
        this.arr = arr;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
    
    
}
