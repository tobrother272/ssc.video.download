/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Model.Log;

/**
 *
 * @author simpl
 */
public class SystemLog {
    public static void showOut(String className, String message){
        System.out.println(className+" "+message);
    }
    public static void showError(String className, String message){
        System.err.println(className+" "+message);
    }
}
