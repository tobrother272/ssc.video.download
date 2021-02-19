/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQliteUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import Utils.StringUtil;

/**
 * @author simplesolution.co
 */
public class Entity {

    private Class c;
    private List<String> arrField;
    private List<String> arrValue;

    /*
    public Entity() {
        this.c = video.getClass();
        arrField = new ArrayList<>();
        arrValue = new ArrayList<>();
        Field[] fields = c.getDeclaredFields();
        System.out.println("The name : " + c.getName().toLowerCase());
        for (int i = 0; i < fields.length; i++) {
            try {
                arrField.add(fields[i].getName());
                fields[i].setAccessible(true);
                if (fields[i].getType().equals("int") || fields[i].getType().equals("long") || fields[i].getType().equals("double") || fields[i].getType().equals("float")) {
                    arrValue.add(fields[i].get(video).toString());
                } else if (fields[i].getType().equals("class java.lang.String")) {
                    arrValue.add("'"+(fields[i].get(video).toString().equals("null")?"":fields[i].get(video).toString())+"'");
                }
            } catch (Exception e) {
            }

            //
        }
        System.out.println(StringUtil.getStringTagsFromList(arrField));
    }
    */

    public String insertData() {

        /*
        String query = "insert into "+c.getName().toLowerCase()+"(video_id,from_time,to_time,start_view,view_per_day) "
                + "values('" + video.getVideoId() + "','"
                + video.getFromTime() + "'," + video.getToTime() + ")";
        return executeQuery(query);
         */
        return "";
    }

}
