/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author tobrother272
 */
public class RandomUtils {
    
    
    public static Color getRandomColor(){
                 Random rand= new Random();
                 float _r = rand.nextFloat();
                 float _g = rand.nextFloat();
                 float _b = rand.nextFloat();
                 return Color.getHSBColor(_r,_g,_b);
    }
   
    
    
    public static List<String> getRandomTag(List<String> _tags){
        List<String> tags = new ArrayList<String>();
        Random rd = new Random();
        for(String tag : _tags){
            if(rd.nextInt(2)==1){
                tags.add(tag);
            }
        }
        return tags;
    }
    public static String generateRandomChars() {
    Random rd = new Random();
    String candidateChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int length =rd.nextInt(10)+4;
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
        sb.append(candidateChars.charAt(random.nextInt(candidateChars
                .length())));
    }
        return sb.toString();
    }
    public static String generateOnlyRandomChars() {
    Random rd = new Random();
    String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int length =1;
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
        sb.append(candidateChars.charAt(random.nextInt(candidateChars
                .length())));
    }
        return sb.toString();
    }
    
    public static String generateOnlyRandomChars(int length) {
    Random rd = new Random();
    String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
        sb.append(candidateChars.charAt(random.nextInt(candidateChars
                .length())));
    }
        return sb.toString();
    }
    public static String generateOnlyRandomNumber(int length) {
    Random rd = new Random();
    String candidateChars = "123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
        sb.append(candidateChars.charAt(random.nextInt(candidateChars
                .length())));
    }
        return sb.toString();
    }
    
    public static String getRandomStringFromFile(String filePath){
        ArrayList<String> result= new ArrayList<>();
        try {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));         
            String line;
            while ((line = br.readLine()) != null) {
                if(!result.contains(line.trim())){
                    result.add(line.trim());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.get(new Random().nextInt(result.size()));
    }
   
    public static void createRandomGraphic(Graphics g,int left , int top , int width , int height,Random rd){      
        switch(rd.nextInt(4)){
            case 0:
                g.fill3DRect(left, top, width, height, true);
                break;
            case 1:
                g.fillOval(left, top,  width, height);
                break;
            case 2:
                g.fillRect(left, top,  width, height);
                break;
            case 3:
                g.fillRoundRect(left, top, width, height, 50, 50);
                break;
        }
    }  
}
