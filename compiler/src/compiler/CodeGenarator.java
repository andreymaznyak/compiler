/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler;

import java.util.ArrayList;

/**
 *
 * @author Andry
 */
public class CodeGenarator {
    
    private String CText;
    private ArrayList<MetaTokenParser> tokenList = null;
    private Tree <TokenParser> parseTree = null;
    
    private String traversalOfTheTree(Tree<TokenParser> list){
        String result = "";
        String currentText = "";
        if(list.getHead().isBlock()){
            currentText = " ";
        }else{
            currentText = list.getHead().getText();
        }
        
        String translate = translateToken(currentText);
        if(translate.equals("")){
            result = result.concat(currentText);
        }else{
            result = result.concat(translate);
        }
        
        for (Tree<TokenParser> child : list.getSubTrees()) {
            result = result.concat(traversalOfTheTree(child));
        }
        return result;
    }
    
    private String translateToken(String token) {
        String result = "";
        MetaTokenParser metaFindToken = null;
        for (MetaTokenParser metaToken : tokenList) {
            if (token != null) {
                if (metaToken.getSyntaxPascal().equals(token)) {
                    metaFindToken = metaToken;
                    result = metaFindToken.getSyntaxC();
                }
            }
        }
        
        return result;
    }
    
    public CodeGenarator(Tree <TokenParser> ParseTree, ArrayList<MetaTokenParser> TokenList){
        parseTree = ParseTree;
        tokenList = TokenList;
    }
    
    public String getCText(){
        
        CText = "";
        //Первоначально меняем некоторые синтаксические конструкции
        for (Tree<TokenParser> child : parseTree.getSubTrees()) {
            if(child.getHead().equals("BlockProgram")){
                clearAllChildrens( child );
            }else{
                 for(Tree<TokenParser> childOfChild : child.getSubTrees()) {
                    switch(childOfChild.getHead().getText()){
                        case "constblock":{
                            shiftChildrens(childOfChild,"="," ");
                            break;
                        }
                        case "var":{
                            //clearChildrens(childOfChild,"var");
                            break;
                        }
                        case "mainblock":{
                            for(Tree<TokenParser> childOfChildofChild : childOfChild.getSubTrees()) {
                                childOfChildofChild.getHead().setText("void main(){\n");
                                break;
                            }
                            break;
                        }
                    }
                } 
            }
        }
        CText = traversalOfTheTree(parseTree);
        //Затем обходим дерево в глубину
        
        return CText;
    }
    private void shiftChildrens(Tree<TokenParser> list, String Condition, String NewText){
        if(list.getHead().getText().equals(Condition)){
            list.getHead().setText(NewText);
        }
        for (Tree<TokenParser> child : list.getSubTrees()) {
            shiftChildrens( child , Condition, NewText );
        }
    }
    //очистка листьев по условию
    private void clearChildrens(Tree<TokenParser> list, String Condition){
        if(list.getHead().getText().equals(Condition)){
            list.getHead().setText("");
        }
        for (Tree<TokenParser> child : list.getSubTrees()) {
            clearChildrens( child, Condition );
        }
    }
    //очистка всех детей
    private void clearAllChildrens(Tree<TokenParser> list){
        list.getHead().setText("");
        for (Tree<TokenParser> child : list.getSubTrees()) {
            clearAllChildrens( child );
        }
    }
//    public String parse(ArrayList<TokenParser> parseTokenList) {
//        String result = "";
//
//        for (TokenParser token : parseTokenList) {
//
//            MetaTokenParser metaToken = findToken(token.getText());
//            if (metaToken != null) {
//                result = result.concat(metaToken.getSyntaxC());
//                if (metaToken.getSyntaxC().equals("{") || metaToken.getSyntaxC().equals("}")) {
//                    result = result.concat("\n");
//                } else {
//                    result = result.concat(" ");
//                }
//            } else {
//                result = result.concat(token.getText());
//                if (token.equals(";")) {
//                    result = result.concat("\n");
//                } else {
//                    result = result.concat(" ");
//                }
//            }
//        }
//        result.concat(result);
//
//        return result;
//
//    }
}
