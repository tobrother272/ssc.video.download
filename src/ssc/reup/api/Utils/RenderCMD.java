/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import java.util.Random;

/**
 *
 * @author inet
 */
public class RenderCMD {
    private int time;
    private String image;
    public RenderCMD(int time,String image){
        this.time=time;
        this.image=image;
    }
    public String getRenderBGString(){
        String cmd ="";
        int mode = new Random().nextInt(2);
        switch(mode){
            case 0:
                cmd ="zoompan=z='zoom+0.001':d=25*" + time + ",boxblur=10";
                break;
            case 1:
                cmd ="zoompan=x='(iw-0.625*ih)/2':y='(1-on/(25*"+time+"))*(ih-ih/zoom)':z='if(eq(on,1),2.56,zoom+0.001)':d=25*"+time+",boxblur=10";
                break;    
//            case 2:
//                cmd ="zoompan=z='if(eq(on,1),1,zoom+0.000417)':fps=25:d=25*"+time+",boxblur=10";
//                break;     
//            case 3:
//                cmd ="zoompan=z='if(lte(zoom,1.0),1.5,max(1.001,zoom-0.0015))':fps=25:d=25*" + time + ",boxblur=10";
//                break; 
//            case 4:
//                cmd ="zoompan=y='ih/4':x='(1-on/(25*"+time+"))*(ih+ih/zoom)/2':z='if(eq(on,1),2.56,zoom+0.001)':d=25*"+time+",boxblur=10";
//                break;         
        }
        return cmd;
    }
    public String getRenderOGString(int mode){
        String cmd ="";
        switch(mode){
            case 0:
                cmd ="overlay=enable='between=(t,0," + time + ")':x=0+t*(main_w/" + time + "/2):y=(main_h-overlay_h)/2";
                break;   
            case 1:
                cmd ="overlay=enable='between=(t,0," + time + ")':y=main_h/4-t*(main_h/" + time*2 + "/2):x=(main_w-overlay_w)/2";
                break;    
//            case 2:
//                cmd ="overlay=enable='between=(t,0," + time + ")':y=main_h/4-t*(main_h/" + time*2 + "/2):x=(main_w-overlay_w)/2";
//                break;        
            case 2:
                cmd ="overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2,zoompan=z='min(max(zoom,pzoom)+0.004,1.2)':d=1:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)'";
                break;      
        }
        return cmd;
    }
    
    
    
    
    
    
    
    
    
}
