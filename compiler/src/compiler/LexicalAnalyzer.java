/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Mikhail
 */
public class LexicalAnalyzer {
    
    public ArrayList<TokenParser> Parse(String text){
        ArrayList<TokenParser> tokenList = new ArrayList<TokenParser>();
        StringTokenizer strTokenizer = new StringTokenizer(text, " \t\n\r\f(),.\'\"[]=+:*/;-", true);
        int count = strTokenizer.countTokens();
        int j = 0; //Номер символа символа
        int k = 0; //Номер строки
        for (int i = 0; i < count; i++) {
            String token = strTokenizer.nextToken();

            if (!token.equals(" ") && !token.equals("\t") && !token.equals("\n") && !token.equals("\r") && !token.equals("\f")) {
                // System.out.println(token);
                text.lastIndexOf(text);
                tokenList.add(new TokenParser(token.trim(), k, j));
                j += token.length();
            } else {
                if (token.equals("\n")) {
                    j = 0;
                    k++;
                } else {
                    j += token.length();
                }

            }
        }
        int tokenListSize = tokenList.size() - 1;
        for (int i = 0; i < tokenListSize; i++) {
            if (tokenList.get(i).equals(":") && tokenList.get(i + 1).equals("=")
                    || tokenList.get(i).equals(".") && tokenList.get(i + 1).equals(".")) {
                tokenList.set(i, tokenList.get(i).concat(tokenList.get(i + 1)));
                tokenList.remove(i + 1);
                tokenListSize--;
            }
            if(tokenList.get(i).equals("end") && tokenList.get(i + 1).equals(".")){
               tokenList.set(i, tokenList.get(i).concat("."));
               tokenList.remove(i + 1);
            }
            if (tokenList.get(i).equals("'")) {
                int n = i;
                while (!(n + 1 >= tokenListSize) && !tokenList.get(n + 1).equals("'")) {
                    tokenList.set(n, tokenList.get(n).concat(tokenList.get(n + 1)));
                    tokenList.remove(n + 1);
                    tokenListSize--;
                }
                if (n + 1 == tokenListSize) {
                    return null;
                }
                if (tokenList.get(n + 1).equals("'")) {
                    tokenList.set(n, tokenList.get(n).concat(tokenList.get(n + 1)));
                    tokenList.remove(n + 1);
                    tokenListSize--;
                }
            }
        }
        
        return tokenList;
    }
    
}
