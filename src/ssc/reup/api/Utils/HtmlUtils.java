/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author inet
 */
public class HtmlUtils {

    public static String removeHtml(String s) {
        String STRIPPED_CHARS = " \t\u00A0\u1680\u180e\u2000\u200a\u202f\u205f\u3000";
        return StringUtils.strip(s, STRIPPED_CHARS);
        
    }
}
