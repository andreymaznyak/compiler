/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler;

/**
 *
 * @author Andry
 */
public class CodeGenarator {
    
    private Tree <TokenParser> parseTree = null;
    
    public CodeGenarator(Tree <TokenParser> ParseTree){
        parseTree = ParseTree;
    }
    
    
}
