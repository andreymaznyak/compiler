/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andry
 */
public class PascalParser {
   
    // <editor-fold defaultstate="collapsed" desc="Описание свойств">
    private static final int otherIdentifer = 0;
    private static final int globalIdentifer = 1;
    private static final int localIdentifer = 2;
    private static final int counstIdentifer = 3;
    private static final int procedureIdentifer = 4;
    private static final int useVariable = 5;
    private static final int initVariable = 6;
    
    private static final boolean nextTokenTRUE = true;
    private static final boolean nextTokenFALSE = false;
    private static final boolean showErrorTRUE = true;
    
    private static final String globalNameSpase = "%global%";
    private static final String procedureNameSpase = "%procedure%";
    
    private String currentNameSpase = globalNameSpase;
    private int i;
    private ArrayList<MetaTokenParser> tokenList = new ArrayList<MetaTokenParser>();
    private ArrayList<TokenParser> parseTokenList = new ArrayList<TokenParser>();
    private ArrayList<TokenParser> listOfNewVariable = new ArrayList<TokenParser>();
    private ArrayList<String> listOfReservedWords = new ArrayList<String>();
    private Map<String,TokenParser> tableVariable = new HashMap<String, TokenParser>();
    
    private ErrorLog errorLog = new ErrorLog();  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Функции инициализации">
    public PascalParser() {

        addMetaToken(new MetaTokenParser(":=", "=", ""));
        addMetaToken(new MetaTokenParser("=", "==", ""));
        addMetaToken(new MetaTokenParser("then", "{", ""));
        addMetaToken(new MetaTokenParser("begin", "{", ""));
        addMetaToken(new MetaTokenParser("if", "if", ""));
        addMetaToken(new MetaTokenParser("for", "for", ""));
        addMetaToken(new MetaTokenParser("end", "}", ""));
        addMetaToken(new MetaTokenParser("else", "else", ""));
        addMetaToken(new MetaTokenParser("end.", "}", ""));
        addMetaToken(new MetaTokenParser("read", "cin <<", ""));
        addMetaToken(new MetaTokenParser("write", "cout >>", ""));
        addMetaToken(new MetaTokenParser("readln", "cin <<", ""));
        addMetaToken(new MetaTokenParser("writeln", "cout >>", ""));
        addMetaToken(new MetaTokenParser("program", "void main", ""));
        
    }
    
    private void init(ArrayList<TokenParser> ParseTokenList){
        i = 0;
        parseTokenList = ParseTokenList;
    } 
    
    public void addMetaToken(MetaTokenParser token) {
        tokenList.add(token);
        listOfReservedWords.add(token.getSyntaxPascal());
    } // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Функции проверяющие токены">
    private boolean currentTokenEquals(boolean nextToken, String regex,  boolean ShowError){
        boolean result = parseTokenList.get(i).toLowerCase().equals(regex);
        if(ShowError){
            errorLog.add(parseTokenList.get(i));
        }
        if(nextToken){
            nextToken();
        }
        return result;
    }
    
    private boolean currentTokenEquals(boolean nextToken, String regex){
        boolean result = parseTokenList.get(i).toLowerCase().equals(regex);
        if(nextToken){
            nextToken();
        }
        return result;
    }
    
    private boolean currentTokenEquals(String regex){
        boolean result = parseTokenList.get(i).toLowerCase().equals(regex);
        return result;
    }
    
    private boolean currentTokenNumber(boolean nextToken){
        boolean result = isNumber(parseTokenList.get(i));
        if(nextToken){
            nextToken();
        }
        return result;
    }
    
    private boolean currentTokenNumber(boolean nextToken,boolean showError){
        boolean result = isNumber(parseTokenList.get(i));
        if(!result){
            if(showError)
            errorLog.add("Ожидается целое число",parseTokenList.get(i));
        }
        if(nextToken){
            nextToken();
        }
        return result;
    }
    //Используется только для проверки идентификатора программы
    private boolean currentTokenIdetifier(boolean nextToken,boolean showError) {
        boolean result = isIdentifier(parseTokenList.get(i));
        if (!result) {
            if(showError)
            errorLog.add("Ожидается идентификатор\n", parseTokenList.get(i));
        }
        if(nextToken){
            nextToken();
        }
        return result;
    }
    // status - означает тип идентификатора если 1 то глобальный если 2 то локальный если 0 то другой
    private boolean currentTokenIdetifier(boolean nextToken, int status) {
        //Нужно дописать с учетом областей видимости получение списков уже объявленных идентификаторов
        boolean result = currentTokenIdetifier(nextToken, status, false);
        return result;
    }
    
    private boolean currentTokenIdetifier(boolean nextToken, int status, boolean showError){
         boolean result = false;
         result = currentTokenIdetifier(nextToken, status, showError, "");
         return result;
    }
    
    private boolean currentTokenIdetifier(boolean nextToken, int status, boolean showError, String type) {
        //Нужно дописать с учетом областей видимости получение списков уже объявленных идентификаторов
        boolean result = false;
        String prefix = "";
        if (status < useVariable) { //Объявление идентификатора
            if (status == 1 || status == 2 || status == 3) {
                prefix = currentNameSpase;
            } else if (status == 4) {
                prefix = procedureNameSpase;
                currentNameSpase = parseTokenList.get(i).getText();
            }
            String key = prefix.concat(parseTokenList.get(i).getText());
            result = isIdentifier(parseTokenList.get(i));
            if (!result) {
                if (tableVariable.containsKey(key) && status == otherIdentifer) {
                    if (status != otherIdentifer) {
                        tableVariable.put(key, parseTokenList.get(i));
                        listOfNewVariable.add(parseTokenList.get(i));
                        if (status == counstIdentifer) {
                            parseTokenList.get(i).setCounst(true);
                        }
                    }
                } else {
                    if (showError) {
                        errorLog.add("Повторно объявленный идентификатор\n", parseTokenList.get(i));
                    }
                }
            } else {
                if (showError) {
                    errorLog.add("Ожидается идентификатор\n", parseTokenList.get(i));
                }
            }
        }else{//идентификатор встречающийся уже в коде
            TokenParser token = null;
            //Проверяем константы и глобальные переменные
            if (tableVariable.containsKey(globalNameSpase.concat(parseTokenList.get(i).getText()))){
                token = tableVariable.get(globalNameSpase.concat(parseTokenList.get(i).getText()));
            }
            //Сначала ищем локальные переменные
            if (tableVariable.containsKey(currentNameSpase.concat(parseTokenList.get(i).getText()))){
                token = tableVariable.get(currentNameSpase.concat(parseTokenList.get(i).getText()));
            }
            
            if(token != null){
                if(token.getType() != type){
                    errorLog.add("Несоотвествие типов ожидается тип ".concat(type.concat("\n")), token);
                }
                if(!token.getInit() && status != initVariable){
                    errorLog.add("Не инициализированный идентификтор\n", parseTokenList.get(i));
                }
                else if(status == initVariable){
                    token.setInit(true);
                }
            }
            else{
                errorLog.add("Необъявленный идентификатор\n", parseTokenList.get(i));
            }
        }
        if (nextToken) {
            nextToken();
        }
        return result;
    }
    
    private boolean currentTokenVariable(){
        boolean result = false;
        return result;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Функции использующие регулярные выражения для проверки токенов">
    private boolean isIdentifier(String token){
        return token.toLowerCase().matches("[_a-zA-Z$][_a-zA-Z0-9$]*")
                && listOfReservedWords.indexOf(token) == -1;
    }
    
    private boolean isIdentifier(TokenParser token){
        return token.getText().toLowerCase().matches("[_a-zA-Z$][_a-zA-Z0-9$]*")
                && listOfReservedWords.indexOf(token.getText()) == -1;
    }
    
    private boolean isNumber(String token){
        return token.matches("^(0|(\\+)?([1-9]{1}[0-9]{0,3})|([1-5]{1}[0-9]{1,4}|[6]{1}([0-4]{1}[0-9]{3}|[5]{1}([0-4]{1}[0-9]{2}|[5]{1}([0-2]{1}[0-9]{1}|[3]{1}[0-5]{1})))))$");
    }
    
    private boolean isNumber(TokenParser token){
        return token.getText().matches("^(0|(\\+)?([1-9]{1}[0-9]{0,3})|([1-5]{1}[0-9]{1,4}|[6]{1}([0-4]{1}[0-9]{3}|[5]{1}([0-4]{1}[0-9]{2}|[5]{1}([0-2]{1}[0-9]{1}|[3]{1}[0-5]{1})))))$");
    } // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Функции проверяющие последовательности токенов">
    
    //функция проверяет идущие через запятую идентификатора пока не дойдет до токена указаного в параметре condition
    //вид: identifer1 {,identiferN}*
    private boolean currentBlockComma(String condition, int isglobalIdentifer, boolean showError) {
        boolean error = false;
        while (!currentTokenEquals(nextTokenTRUE, condition) && !error) {
            error = !currentTokenEquals(nextTokenTRUE, ",", showError);
            error = !currentTokenIdetifier(nextTokenTRUE, isglobalIdentifer,  showError);
        }
        return !error;
    }
    //Ожидаем открывающую скобку или точку с запятой (identifer1 {,identiferN}*); or ;
    private boolean currentBlockBracketAndIdentifer(boolean nextToken,boolean showError) {
        boolean error = false;
        if (currentTokenEquals(nextTokenTRUE,"(")) {
            //Ожидаем идектификатор файла
            error = !currentTokenIdetifier(nextTokenTRUE, showError);
            error = !currentBlockComma(")", otherIdentifer, showError);
        }
        if(!nextToken)
            backToken();
        return !error;
    }
    //Если видим блок описания констант counst identifer = 1; {identiferN = N}*
    private boolean currentBlockCounst(boolean nextToken,boolean showError){
        boolean result = false;
        if(currentTokenEquals(nextTokenTRUE, "counst")){

            boolean error = false;
            //Пока не встретим вар или бегин или ошибку
            while(!currentTokenEquals("begin")&&!currentTokenEquals("procedure")&&!currentTokenEquals("var")&&!currentTokenEquals("counst") && !error ){
                //Ожидаем идентификатор
                
                error = !currentTokenIdetifier(nextTokenTRUE, counstIdentifer, showError); 
                //Ожидаем равно
                error = !currentTokenEquals(nextTokenTRUE, "=", showError);
                //Ожидаем целочисленную константу
                error = !currentTokenNumber(nextTokenFALSE, showError);
                if(!error){
                    for(TokenParser token: listOfNewVariable){
                        token.setType("integer");
                        token.setValue(parseTokenList.get(i).getText());
                    }
                    listOfNewVariable.clear();
                }
                //Ожидаем точку с запятой
                error = !currentTokenEquals(nextTokenTRUE, ";", showError);
            }
            result = !error;
        }
        if(!nextToken)
            backToken();
        return result;
    }
    //текущий токен целочисленная константа
    private int currentTokenGetValueCounstNumber(boolean NextToken) {
        int result = -1;
        if (!isNumber(parseTokenList.get(i))) {
            TokenParser token = tableVariable.get(currentNameSpase.concat(parseTokenList.get(i).getText()));
            if (token != null) {
                if (token.getType().equals("integer")) {
                    try {
                        result = Integer.valueOf(parseTokenList.get(i).getValue());
                    } catch (NumberFormatException e) {
                        errorLog.add("Ошибка приведения к int", token);
                    }

                } else {
                    errorLog.add("Недопустимый тип размерности массива", token);
                }
            } else {
                errorLog.add("Неизвестный идентификатор", parseTokenList.get(i));
            }
        } else {
            try {
                result = Integer.valueOf(parseTokenList.get(i).getText());
            } catch (NumberFormatException e) {
                errorLog.add("Ошибка приведения к int", null);
            }
        }
        if(NextToken)
            nextToken();
        return result;
    }
    //текущий блок описание типов
    private boolean currentBlockType(){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
                case "integer":{
                    for(TokenParser token: listOfNewVariable){
                        token.setType("integer");
                    }
                    listOfNewVariable.clear();
                    nextToken();
                    break;
                }
                case "real":{
                    for(TokenParser token: listOfNewVariable){
                        token.setType("real");
                    }
                    listOfNewVariable.clear();
                    nextToken();
                    break;
                }
                case "array":{
                    for(TokenParser token: listOfNewVariable){
                        token.setType("array");
                    }
                    listOfNewVariable.clear();
                    nextToken();
                    currentTokenEquals(nextTokenTRUE, "[", showErrorTRUE);
                    int lowIndex = currentTokenGetValueCounstNumber(nextTokenTRUE);
                    currentTokenEquals(nextTokenTRUE, "..", showErrorTRUE);
                    int topIndex = currentTokenGetValueCounstNumber(nextTokenTRUE);
                    if(lowIndex >= topIndex){
                        errorLog.add("Нижняя граница массива превосходит верхнюю", parseTokenList.get(i));
                    }
                    currentTokenEquals(nextTokenTRUE, "]", showErrorTRUE);
                    
                    currentTokenEquals(nextTokenTRUE, "of", showErrorTRUE);
                    
                    currentTokenEquals(nextTokenTRUE, "integer", showErrorTRUE);
                    break;
                }
                case "char":{
                    for(TokenParser token: listOfNewVariable){
                        token.setType("char");
                    }
                    listOfNewVariable.clear();
                    nextToken();
                    break;
                }
                default:{
                    
                } 
            }       
        return result;
    }
    
    private boolean currentBlockVar(boolean nextToken,int status ,boolean showError){
        boolean result = false;
        if(currentTokenEquals(nextTokenTRUE,"var")){
            //Ожидаем идектификатор файла
            currentTokenIdetifier(nextTokenTRUE,status,showError);
            //Пока не дойдём до : или не встретим ошибку
            currentBlockComma(":", status, showError);
            //Ожидаем блок описания типов
            currentBlockType();
            //Ожидаем точку с запятой
            currentTokenEquals(nextTokenTRUE, ";", showError);
            while(!currentTokenEquals("begin")&&!currentTokenEquals("procedure")&&!currentTokenEquals("var")&&!currentTokenEquals("counst")){
                //Ожидаем идектификатор файла
                currentTokenIdetifier(nextTokenTRUE,status,showError);
                //Пока не дойдём до : или не встретим ошибку
                currentBlockComma(":", status, showError);
                //Ожидаем блок описания типов
                currentBlockType();
                //Ожидаем точку с запятой
                currentTokenEquals(nextTokenTRUE, ";", showError);
            }
        }
        return result;
    }
    
    private boolean currentBlockGlobalDeclarations(boolean nextToken, boolean showError){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "counst":{
                //Если видим блок описания констант counst identifer = 1; {identiferN = N}*
                currentBlockCounst(nextToken,showError);
                currentBlockGlobalDeclarations(nextToken, showError);
                break;
            }
            case "var":{
                currentBlockVar(nextToken, globalIdentifer, showError);
                currentBlockGlobalDeclarations(nextToken, showError);
                break;
            }
            case "procedure":{
                currentBlockProcedure(nextToken,showError);
                currentBlockGlobalDeclarations(nextToken, showError);
                
                break;
            }
            case "begin":{
                currentNameSpase = globalNameSpase;
                currentTokenEquals(nextToken, "begin", showError);
                
                break;
            }
            default:{
                break;
            }
        }
        return result;
    }
    
    private boolean currentBlockLocalDeclarations(boolean nextToken, boolean showError){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "var":{
                currentBlockVar(nextToken, localIdentifer, showError);
                currentBlockGlobalDeclarations(nextToken, showError);
                break;
            }
            case "begin":{
                break;
            }
            default:{
                break;
            }
        }
        return result;
    }
    
    private boolean currentBlockProcedure(String procedureName, int status, boolean showError){
        boolean result = false;
        currentTokenEquals(nextTokenTRUE, procedureName, showError);
        currentTokenEquals(nextTokenTRUE, "(", showError);
        currentBlockComma(")", status, showError);
        currentTokenEquals(nextTokenTRUE, ";", showError);
        return result;
    }
    
    private boolean currentBlockOperations(String type){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "(":{
                break;
            }
            default:{ //Первый символ скобка или идентификатор
                
            }
        }
        return result;
    }
    
    private boolean currentTokenMathLogicalOperations(String lastOperation){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "+":{
                currentTokenEquals(nextTokenTRUE, "+", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int,real");
                break;
            }
            case "-":{
                currentTokenEquals(nextTokenTRUE, "-", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int,real");
                break;
            }
            case "*":{
                currentTokenEquals(nextTokenTRUE, "*", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case "/":{
                currentTokenEquals(nextTokenTRUE, "/", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case ">":{
                currentTokenEquals(nextTokenTRUE, ">", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case ">=":{
                currentTokenEquals(nextTokenTRUE, ">=", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case "<":{
                currentTokenEquals(nextTokenTRUE, "<", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case "<=":{
                currentTokenEquals(nextTokenTRUE, "<=", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case "<>":{
                currentTokenEquals(nextTokenTRUE, "<>", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                break;
            }
            case "=":{
                currentTokenEquals(nextTokenTRUE, "=", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "");
                break;
            }
            case "or":{
                currentTokenEquals(nextTokenTRUE, "or", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "boolean");
                break;
            }
            case "and":{
                currentTokenEquals(nextTokenTRUE, "and", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "boolean");
                break;
            }
            case "not":{
                currentTokenEquals(nextTokenTRUE, "not", showErrorTRUE);
                currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "boolean");
                break;
            }
            case "(":{
                break;
            }
            case ")":{
                break;
            }
            default:{ //Первый символ скобка или идентификатор
                
            }
            currentTokenMathLogicalOperations(lastOperation);
        }
        return result;
    }
    
    private boolean currentBlockReadLn(boolean showError){
        boolean result = false;
        currentTokenEquals(nextTokenTRUE, "readln", showError);
        currentTokenEquals(nextTokenTRUE, "(", showError);
        currentBlockComma(")", useVariable, showError);
        currentTokenEquals(nextTokenTRUE, ";", showError);
        return result;
    }
    
    private boolean currentBlockIf(){
        boolean result = false;
        currentTokenEquals(nextTokenTRUE, "if", showErrorTRUE);
        currentTokenIdetifier(nextTokenTRUE, i, showErrorTRUE, globalNameSpase);
        return result;
    }
    private boolean currentBlockBegin(boolean nextToken, boolean showError){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "readln":{
                currentBlockProcedure("readln", initVariable, showError);
                break;
            }
            case "writeln":{
                currentBlockProcedure("writeln", useVariable, showError);
                break;
            }
            case "read":{
                currentBlockProcedure("read", initVariable, showError);
                break;
            }
            case "write":{
                currentBlockProcedure("write", useVariable, showError);
                break; 
            }
            case "for":{
                
                break;
            }
            case "if":{
                break;
            }
            
            //ЕЩЁ может быть идентификатор 
        }
        return result;
    }
    private boolean currentBlockProcedure(boolean nextToken, boolean showError){
        boolean result = false;
        if(currentTokenEquals(nextTokenTRUE, "procedure", showErrorTRUE)){
            currentTokenIdetifier(nextTokenTRUE, procedureIdentifer, showError);
            currentTokenEquals(nextTokenTRUE, "(", showErrorTRUE);
            //Ожидаем идектификатор параметра
            currentTokenIdetifier(nextTokenTRUE, localIdentifer,showError);
            //Пока не дойдём до : или не встретим ошибку
            currentBlockComma(":", localIdentifer, showError);
            //Ожидаем блок описания типов
            currentBlockType();
            boolean error = false;
            while(!currentTokenEquals(")")&&!error){
                //Ожидаем точку с запятой
                currentTokenEquals(nextTokenTRUE, ";", showError);
                //Ожидаем идектификатор файла
                error = !currentTokenIdetifier(nextTokenTRUE,localIdentifer,showError);
                //Пока не дойдём до : или не встретим ошибку
                error = !currentBlockComma(":", localIdentifer, showError);
                //Ожидаем блок описания типов
                error = !currentBlockType();
                //Ожидаем точку с запятой
            }
            nextToken();
            currentTokenEquals(nextToken, ";", showError);
            currentBlockLocalDeclarations(nextToken, showError);
            
        }
        return result;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Служебные функции">
    public String parseNew(ArrayList<TokenParser> ParseTokenList) {
        init(ParseTokenList);
        String result = "";
        

        //Ожидаем токен объявления программы Program
        currentTokenEquals(nextTokenTRUE, "program", showErrorTRUE);
        //Ожидаем токен-идентификатор программы identifer
        currentTokenIdetifier(nextTokenTRUE, showErrorTRUE);
        //Ожидаем открывающую скобку или точку с запятой (identifer1 {,identiferN}*); or ;
        currentBlockBracketAndIdentifer(nextTokenTRUE,showErrorTRUE);

        currentTokenEquals(nextTokenTRUE,";", showErrorTRUE);
        
        currentBlockGlobalDeclarations(nextTokenTRUE, showErrorTRUE);
        
        
        
        if(parseTokenList.get(i).toLowerCase().equals("begin")){
                    
        }
        else
            errorLog.add("Ожидается begin\n",parseTokenList.get(i));
        
            
                   
       
        
        //Ожидаем начало блока
        //Ожидаем операнды
        //Ожидаем логическое выражение
        //Ожидаем конец блока
        /*for (String token : parseTokenList) {

            MetaTokenParser metaToken = findToken(token);
            if (metaToken != null) {
                result = result.concat(metaToken.getSyntaxC());
                if (metaToken.getSyntaxC().equals("{") || metaToken.getSyntaxC().equals("}")) {
                    result = result.concat("\n");
                } else {
                    result = result.concat(" ");
                }
            } else {
                result = result.concat(token);
                if (token.equals(";")) {
                    result = result.concat("\n");
                } else {
                    result = result.concat(" ");
                }
            }
        }*/
        result.concat(result);

        return result;

    }
    
    public String parse(ArrayList<TokenParser> parseTokenList) {
        String result = "";

        for (TokenParser token : parseTokenList) {

            MetaTokenParser metaToken = findToken(token.getText());
            if (metaToken != null) {
                result = result.concat(metaToken.getSyntaxC());
                if (metaToken.getSyntaxC().equals("{") || metaToken.getSyntaxC().equals("}")) {
                    result = result.concat("\n");
                } else {
                    result = result.concat(" ");
                }
            } else {
                result = result.concat(token.getText());
                if (token.equals(";")) {
                    result = result.concat("\n");
                } else {
                    result = result.concat(" ");
                }
            }
        }
        result.concat(result);

        return result;

    }
    
    private MetaTokenParser findToken(String token) {
        MetaTokenParser result = null;
        for (MetaTokenParser metaToken : tokenList) {
            if (token != null) {
                if (metaToken.getSyntaxPascal().equals(token)) {
                    result = metaToken;
                }
            }
        }
        return result;
    }
    
    private void nextToken(){
        i++;
    }
    
    private void backToken(){
        i--;
    }// </editor-fold>
    
}

// <editor-fold defaultstate="collapsed" desc="Класс ошибок">
class ErrorLog{
    String ErrorLog = "";
    ;
    public ErrorLog(){
        
    }
    public void add(String error, TokenParser token){
        ErrorLog.concat("Ошибка в строке:");
        ErrorLog.concat(String.valueOf(token.getLine()));
        ErrorLog.concat(" символ:");
        ErrorLog.concat(String.valueOf(token.getSymbol()));
        ErrorLog.concat(" -".concat(error));
    }
    
    public void add(TokenParser token){
        ErrorLog.concat("Ошибка в строке:");
        ErrorLog.concat(String.valueOf(token.getLine()));
        ErrorLog.concat(" символ:");
        ErrorLog.concat(String.valueOf(token.getSymbol()));
        ErrorLog.concat(" -".concat("Ожидается ".concat(token.getText())));
    }
}// </editor-fold>