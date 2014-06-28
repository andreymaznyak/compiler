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
class MetaTokenParser extends Object{

    private String syntaxPascal;
    private String syntaxC;
    private String type;
    private ContextMetaToken leftContext;
    private ContextMetaToken rightContext;
    private boolean isregex;
    
    public MetaTokenParser(String SyntaxPascal, String SyntaxC, String Type){
        syntaxPascal = SyntaxPascal;
        syntaxC      = SyntaxC;
        type         = Type;
        isregex      = false;
    }
    public MetaTokenParser(String new_syntaxPascal, String new_syntaxC, String new_type, boolean regex){
        syntaxPascal = new_syntaxPascal;
        syntaxC      = new_syntaxC;
        type         = new_type;
        isregex      = regex;
    }
    
    public void setLeftContext( ContextMetaToken new_leftContext ){
        leftContext = new_leftContext;
    }
    
    public void setRightContext( ContextMetaToken newRightContext ){
        rightContext = newRightContext;
    }
     
    public ContextMetaToken getLeftContext() {
        return leftContext;
    }
    
    public ContextMetaToken getRightContext() {
        return rightContext;
    }
    
    public String getType(){
        return type;
    }
    
    public String getSyntaxPascal(){
        return syntaxPascal;
    }
    
    public String getSyntaxC(){
        return syntaxC;
    }
            
}

class ContextMetaToken {

    private boolean typeOR;
    private MetaTokenParser[] tokens;
}

class TokenParser{
    private int line;
    private int symbol;
    private String text;
    private String type;
    private boolean init;
    private boolean counst;
    private String value;
    private boolean list;
    
    public TokenParser(String Text, int Line, int Symbol) {
        text = Text;
        line = Line + 1;
        symbol = Symbol;
        counst = false;
        list = false;
    }
    
    public TokenParser(String Text, boolean List){
        text = Text;
        list = List;
    }
    
    public boolean equals(String string){
        return text.equals(string);
    }
    /*public String concat(String string){
        return text.concat(string);
    }*/
    public TokenParser concat(TokenParser string){
        text = text.concat(getText());
        return this;
    }
    public String toLowerCase(){
        return text.toLowerCase();
    }
    public String getText(){
        return text.toLowerCase();
    }
    public int getLine(){
        return line;
    }
    public int getSymbol(){
        return symbol;
    }
    public String getType(){
        return type;
    }
    public void setType(String Type){
        type = Type;
    }
    public boolean getInit(){
        return init;
    }
    public void setInit(boolean Init){
        init = Init;
    }
    public void setCounst(boolean Counst){
        counst = Counst;
    }
    public boolean getCounst(){
        return counst;
    }
    public void setValue(String Value){
        value = Value;
    }
    public String getValue(){
        return value;
    }
    public String toString(){
        return text;
    }
}



