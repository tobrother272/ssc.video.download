/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

/**
 *
 * @author PC
 */
public class SSCFilterOptionModel {
   private String name;
   private String value;
   public SSCFilterOptionModel(String name, String value) {
        this.name = name;
        this.value = value;
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
   
}
