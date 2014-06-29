/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler;

/**
 *
 * @author Mikhail
 */
public class Formater {
    private static final String tab = "    ";
    
    private String tabstring (int k) {
        String result = "";
        for (int i = 0; i < k; i++) {
            result = result.concat(tab);
        }
        return result;
    }
    
    public String format (String text) {
        int j;
        int l = 0;  // уровень
        String s;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                if (text.charAt(i-1) == '{') {
                    l++;
                }
                if (text.charAt(i+1) == '}') {
                    l--;
                }
                s = tabstring(l);
                text = text.substring(0, i+1) + s + text.substring(i+1);
            }
        }
        return text;
    }
    
}