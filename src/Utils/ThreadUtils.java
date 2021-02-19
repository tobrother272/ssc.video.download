/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author inet
 */
public class ThreadUtils {
    public static ObservableList<String> arrThread = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    public static void startTask(Task task) {
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
    public static void startTask(Task task,Thread t) {
        t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
