/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import java.util.Collection;
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
    private static final boolean BLOCK = true;

    private static final String globalNameSpase = "%global%";
    private static final String procedureNameSpase = "%procedure%";
    
    private String currentNameSpase = globalNameSpase;
    private int i;
    private ArrayList<MetaTokenParser> tokenList = new ArrayList<MetaTokenParser>();
    private ArrayList<TokenParser> parseTokenList = new ArrayList<TokenParser>();
    private ArrayList<TokenParser> listOfNewVariable = new ArrayList<TokenParser>();
    private ArrayList<String> listOfReservedWords = new ArrayList<String>();
    private Map<String,TokenParser> tableVariable = new HashMap<String, TokenParser>();
    private Tree <TokenParser> parseTree = null;
    private Tree <TokenParser> currentList = null;
    public ErrorLog errorLog = new ErrorLog( null );  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Функции инициализации">
    public PascalParser( javax.swing.JTextArea jTextError ) {

        addMetaToken(new MetaTokenParser(":=", "=", ""));
        addMetaToken(new MetaTokenParser("=", "==", ""));
        addMetaToken(new MetaTokenParser("then", ")\n", ""));
        addMetaToken(new MetaTokenParser("begin", "{", ""));
        addMetaToken(new MetaTokenParser("if", "if(", ""));
        addMetaToken(new MetaTokenParser("for", "for", ""));
        addMetaToken(new MetaTokenParser("end", "\n}", ""));
        addMetaToken(new MetaTokenParser("var", "", ""));
        addMetaToken(new MetaTokenParser("const", "", ""));
        addMetaToken(new MetaTokenParser("else", "\nelse\n", ""));
        addMetaToken(new MetaTokenParser("end.", "\n}", ""));
        addMetaToken(new MetaTokenParser("read", "cin <<", ""));
        addMetaToken(new MetaTokenParser("write", "cout >>", ""));
        addMetaToken(new MetaTokenParser("readln", "cin <<", ""));
        addMetaToken(new MetaTokenParser("writeln", "cout >>", ""));
        addMetaToken(new MetaTokenParser("program", "", ""));
        addMetaToken(new MetaTokenParser("integer", "int ", ""));
        addMetaToken(new MetaTokenParser("to", "", ""));
        addMetaToken(new MetaTokenParser("do", "{", ""));
        addMetaToken(new MetaTokenParser("procedure", "\nvoid ", ""));
        addMetaToken(new MetaTokenParser("%variableblock%", "", ""));
        addMetaToken(new MetaTokenParser("%constblock%", "", ""));
        addMetaToken(new MetaTokenParser("%procedureblock%", "", ""));
        addMetaToken(new MetaTokenParser("%mainblock%", "", ""));
        addMetaToken(new MetaTokenParser("%constbodyblock%", "\n#define ", ""));
        addMetaToken(new MetaTokenParser("%blockprogram%", "", ""));
        addMetaToken(new MetaTokenParser("%blockglobaldeclarations%", "", ""));
        addMetaToken(new MetaTokenParser("%globalblock%", "#include <iostream>\nusing namespace std;", ""));
        addMetaToken(new MetaTokenParser("%varglobalblock%", "", ""));
        addMetaToken(new MetaTokenParser("%prefixvariableblock%", "\n", ""));
        addMetaToken(new MetaTokenParser("%OperatorIf%", "", ""));
        addMetaToken(new MetaTokenParser("", "", ""));
        errorLog.setWiget(jTextError);
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
        if(ShowError && !result){
            errorLog.add("Ожидается ".concat(regex),parseTokenList.get(i));
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
    
    private String getTypeCurrentToken(int status){
        String result = "";
        String prefix = "";
        if(!isNumber(parseTokenList.get(i).getText())){
            if (status == 1 || status == 2 || status == 3 || status == 5) {
                prefix = currentNameSpase;
            } else if (status == 4) {
                prefix = procedureNameSpase;
                currentNameSpase = parseTokenList.get(i).getText();
            }
            String key = prefix.concat(parseTokenList.get(i).getText());
            if (tableVariable.containsKey(key)){
                TokenParser token = tableVariable.get(key);
                result = token.getType();
            };
        }else{
            result = "integer";
        }
        return result;
    }
    private boolean currentTokenConst(){
        boolean result = false;
        if (isNumber(parseTokenList.get(i).getText())){
            result = true;
        }
        return result;
    }
    private boolean currentTokenIdetifierOrConst(boolean nextToken, int status, boolean showError, String type){
        boolean result = false;
        if(currentTokenConst()){
            nextToken();
        }
        else{
            
        }
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
            if (result) {
                if (!(tableVariable.containsKey(key) && status == otherIdentifer)) {
                    //лишняя проверка
                    if (status != otherIdentifer) {
                        tableVariable.put(key, parseTokenList.get(i));
                        listOfNewVariable.add(parseTokenList.get(i));
                        if (status == counstIdentifer) {
                            parseTokenList.get(i).setInit(true);
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
                if(!token.getType().equals(type)){
                    errorLog.add("Несоотвествие типов ожидается тип ".concat(type.concat("\n")), token);
                }
                if(!token.getInit() && status != initVariable){
                    errorLog.add("Не инициализированный идентификтор\n", parseTokenList.get(i));
                }
                else if(status == initVariable){
                    token.setInit(true);
                }
            }
            else if(isNumber(parseTokenList.get(i))){
                
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
    private boolean currentBlockComma(String condition, int isglobalIdentifer, boolean showError, boolean conditionNextToken) {
        boolean error = false;
        while (!currentTokenEquals(conditionNextToken, condition) && !error) {
            error = !currentTokenEquals(nextTokenTRUE, ",", showError);
            error = !currentTokenIdetifier(nextTokenTRUE, isglobalIdentifer,  showError);
        }
        if(!error)
            nextToken();
        return !error;
    }
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
    //функция проверяет идущие через запятую идентификатора пока не дойдет до токена указаного в параметре condition
    //вид: identifer1 {,identiferN}*
    private boolean currentBlockComma(String condition, int isglobalIdentifer, boolean showError, int noIdentifir) {
        boolean error = false;
        while (!currentTokenEquals(nextTokenTRUE, condition) && !error) {
            error = !currentTokenEquals(nextTokenTRUE, ",", showError);
            error = !currentTokenIdetifier(nextTokenTRUE, showError);
        }
        return !error;
    }
    //Ожидаем открывающую скобку или точку с запятой (identifer1 {,identiferN}*); or ;
    private boolean currentBlockBracketAndIdentifer(boolean nextToken,boolean showError) {
        boolean error = false;
        switch( parseTokenList.get(i).getText() ){
            case "(":{  
                if (currentTokenEquals(nextTokenTRUE,"(")) {
                    //Ожидаем идектификатор файла
                    error = !currentTokenIdetifier(nextTokenTRUE, showError);
                    error = !currentBlockComma(")", otherIdentifer, showError);
                    error = !currentTokenEquals(nextTokenTRUE, ";", showError);
                }
                break;
            }
            case ";":{
                error = !currentTokenEquals(nextTokenTRUE, ";", showError);
                break;
            }
            default:{
                error = true;
                errorLog.add( "Ожидается ( или ;", parseTokenList.get(i));
            }
        }
        if(!nextToken)
            backToken();
        return !error;
    }
    //Если видим блок описания констант counst identifer = 1; {identiferN = N}*
    private boolean currentBlockCounst(boolean nextToken,boolean showError){
        boolean result = false;
        if(currentTokenEquals(nextTokenTRUE, "const")){

            boolean error = false;
            //Пока не встретим вар или бегин или ошибку
            while(!currentTokenEquals("begin")&&!currentTokenEquals("procedure")&&!currentTokenEquals("var")&&!currentTokenEquals("counst") && !error ){
                //Ожидаем идентификатор
                addLeafBlock(currentList, "%ConstBodyBlock%");
                error = !currentTokenIdetifier(nextTokenTRUE, counstIdentifer, showError); 
                //Ожидаем равно
                error = !currentTokenEquals(nextTokenTRUE, "=", showError);
                //Ожидаем целочисленную константу
                error = !currentTokenNumber(nextTokenTRUE, showError);
                if(!error){
                    for(TokenParser token: listOfNewVariable){
                        token.setType("integer");
                        token.setValue(parseTokenList.get(i - 1).getText());
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
                        result = Integer.valueOf(token.getValue());
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
        boolean error = false;
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
                    error = true;
                    errorLog.add("Не известный тип данных", parseTokenList.get(i));
                    nextToken();
                } 
            }       
        return !error;
    }
    //Текущий блок объявления переменных
    private boolean currentBlockVar(boolean nextToken,int status ,boolean showError){
        boolean result = false;
        if(currentTokenEquals(nextTokenTRUE,"var")){
            Tree<TokenParser> currentLevel = currentList;
            addLeafBlock( currentLevel,"%prefixvariableblock%" );
            addLeafBlock( currentLevel,"%variableBlock%" );
            //Ожидаем идентификатор 
            currentTokenIdetifier(nextTokenTRUE,status,showError);
            //Пока не дойдём до : или не встретим ошибку
            currentBlockComma(":", status, showError, nextTokenFALSE);
            //Ожидаем блок описания типов
            currentBlockType();
            //Ожидаем точку с запятой
            currentTokenEquals(nextTokenTRUE, ";", showError);
            while(!currentTokenEquals("begin")&&!currentTokenEquals("procedure")&&!currentTokenEquals("var")&&!currentTokenEquals("counst")){
                addLeafBlock( currentLevel,"%prefixvariableblock%" );
                addLeafBlock( currentLevel,"%variableBlock%" );
                //Ожидаем идектификатор файла
                currentTokenIdetifier(nextTokenTRUE, status, showError);
                //Пока не дойдём до : или не встретим ошибку
                currentBlockComma(":", status, showError , nextTokenFALSE);

                //Ожидаем блок описания типов
                currentBlockType();
                //Ожидаем точку с запятой
                currentTokenEquals(nextTokenTRUE, ";", showError);
            }
        }
        return result;
    }
    //Текущий блок глобальных объявлений констант, перменных, процедур, главного блока программы 
    private boolean currentBlockGlobalDeclarations(boolean nextToken, boolean showError, Tree<TokenParser> CurrentList){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "const":{
                //Если видим блок описания констант counst identifer = 1; {identiferN = N}*
                addLeafBlock( CurrentList, "%ConstBlock%");
                currentBlockCounst(nextToken,showError);
                currentBlockGlobalDeclarations(nextToken, showError, CurrentList);
                break;
            }
            case "var":{
                addLeafBlock( CurrentList,"%varGlobalBlock%");
                currentBlockVar(nextToken, globalIdentifer, showError);
                currentBlockGlobalDeclarations(nextToken, showError, CurrentList);
                break;
            }
            case "procedure":{
                addLeafBlock( CurrentList,"%ProcedureBlock%");
                currentBlockProcedure(nextToken,showError);
                currentBlockGlobalDeclarations(nextToken, showError, CurrentList);
                
                break;
            }
            case "begin":{
                addLeafBlock( CurrentList,"%MainBlock%");
                currentNameSpase = globalNameSpase;
                currentBlockBegin(nextToken, showError);
                break;
            }
            default:{
                break;
            }
        }
        return result;
    }
    //Текущий блок описания локальных переменных или блок программы(Внутренности процедуры)
    private boolean currentBlockLocalDeclarations(boolean nextToken, boolean showError){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case "var":{
                currentBlockVar(nextToken, localIdentifer, showError);
                nextToken();
                currentBlockLocalDeclarations(nextToken, showError);
                break;
            }
            case "begin":{
                
                currentBlockBegin(nextToken, showError);
                break;
            }
            default:{
                break;
            }
        }
        return result;
    }
    //Текущий блок описания процедуры
    private boolean currentBlockProcedure(String procedureName, int status, boolean showError){
        boolean result = false;
        currentTokenEquals(nextTokenTRUE, procedureName, showError);
        currentTokenEquals(nextTokenTRUE, "(", showError);
        currentTokenIdetifier(nextTokenTRUE, otherIdentifer,  showError);
        currentBlockComma(")", status, showError);
        return result;
    }

    //текущий токен логические операции
    private String currentTokenMathLogicalOperations(String lastOperation){
        String result = "";
        switch(parseTokenList.get(i).getText()){
            case "+":{
                currentTokenEquals(nextTokenTRUE, "+", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int,real");
                result = "integer";
                break;
            }
            case "-":{
                currentTokenEquals(nextTokenTRUE, "-", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int,real");
                result = "integer";
                break;
            }
            case "*":{
                currentTokenEquals(nextTokenTRUE, "*", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "integer";
                break;
            }
            case "/":{
                currentTokenEquals(nextTokenTRUE, "/", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "integer";
                break;
            }
            case ">":{
                currentTokenEquals(nextTokenTRUE, ">", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "boolean";
                break;
            }
            case ">=":{
                currentTokenEquals(nextTokenTRUE, ">=", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "boolean";
                break;
            }
            case "<":{
                currentTokenEquals(nextTokenTRUE, "<", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "boolean";
                break;
            }
            case "<=":{
                currentTokenEquals(nextTokenTRUE, "<=", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "boolean";
                break;
            }
            case "<>":{
                currentTokenEquals(nextTokenTRUE, "<>", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "int");
                result = "boolean";
                break;
            }
            case "=":{
                result = "boolean";
                currentTokenEquals(nextTokenTRUE, "=", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "");
                break;
            }
            case "or":{
                result = "boolean";
                currentTokenEquals(nextTokenTRUE, "or", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "boolean");
                break;
            }
            case "and":{
                result = "boolean";
                currentTokenEquals(nextTokenTRUE, "and", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "boolean");
                break;
            }
            case "not":{
                result = "boolean";
                currentTokenEquals(nextTokenTRUE, "not", showErrorTRUE);
                //currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, "boolean");
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
            //currentTokenMathLogicalOperations(lastOperation);
        }
        return result;
    }
    
    private boolean currentBlockReadLn(boolean showError){
        boolean result = false;
        currentTokenEquals(nextTokenTRUE, "readln", showError);
        currentTokenEquals(nextTokenTRUE, "(", showError);
        currentBlockComma(")", useVariable, showError);
        return result;
    }
    //Текущий блок условие
    private boolean currentBlockConditon(String CurrentType){
        boolean result = false;
        if(i < parseTokenList.size()){
            switch(parseTokenList.get(i).getText()){
                case("("):{
                    currentTokenEquals(nextTokenTRUE, "(", showErrorTRUE);
                    CurrentType = getTypeCurrentToken(useVariable);
                    currentBlockConditon(CurrentType);
                    currentTokenEquals(nextTokenTRUE, ")", showErrorTRUE);
                    break;
                }
                case("then"):{
                    currentTokenEquals(nextTokenTRUE, "then", showErrorTRUE);
                    break;
                }
                default:{
                    CurrentType = getTypeCurrentToken(useVariable);
                    currentTokenIdetifier(nextTokenTRUE, useVariable, showErrorTRUE, CurrentType);
                    currentTokenMathLogicalOperations(CurrentType);
                    currentBlockConditon(CurrentType);
                }
            }
        }
        return result;
    }
    
    private boolean currentBlockIf(){
        boolean result = false;
        addLeafBlock("%OperatorIf%");
        currentTokenEquals(nextTokenTRUE, "if", showErrorTRUE);
        currentBlockConditon("");
        currentBlockOperations();
        if(parseTokenList.get(i).getText().equals("else")){
            currentTokenEquals(nextTokenTRUE, "else", showErrorTRUE);
            currentBlockOperations();
        }
        return result;
    }
    
    private boolean currentBlockOperations(){
        boolean result = false;
        switch(parseTokenList.get(i).getText()){
            case"begin":{
                currentBlockBegin(nextTokenTRUE, showErrorTRUE);
                break;
            }
            default:{
                currentBlockOperator(nextTokenTRUE, showErrorTRUE);
            }
            
        }
        return result;
    }
    //<оп_последовательность> ::= <оператор> | <оп_последовательность>;<оператор>
    private boolean currentBlockOperator(boolean nextToken, boolean showError){
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
                currentBlockIf();
                break;
            }
            case "end.":{
                break;
            }
            case "end":{
                break;
            }
            //ЕЩЁ может быть идентификатор при присваивании
        }
        return result;
    }
    //<составной_оператор> ::= begin <оп_последовательность> end
    private boolean currentBlockBegin(boolean nextToken, boolean showError){
        boolean result = false;
        currentTokenEquals(nextToken, "begin", showError);
        while(!parseTokenList.get(i).getText().equals("end")&&!parseTokenList.get(i).getText().equals("end.")){
            currentBlockOperator(nextToken, showError);
//            currentTokenEquals(nextToken, ";", showError);
        }
        switch(parseTokenList.get(i).getText()){
            case "end":{
               currentTokenEquals(nextToken, "end", showError); 
            }
            case "end.":{
               currentTokenEquals(nextToken, "end.", showError);
            }
        }    
        //ЕЩЁ может быть идентификатор 
        
        return result;
    }
    //Текущий блок процедура
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
    //Текущий блок объявление программы
    private boolean currentBlockProgram(){
        boolean error = false;
        
        //Ожидаем токен объявления программы Program
        if(! currentTokenEquals(nextTokenTRUE, "program", showErrorTRUE) ){
            error = true;
            errorLog.add("Ожидается ключевое слово program", parseTokenList.get(i));
        }
        
            
        //Ожидаем токен-идентификатор программы identifer
        if(! currentTokenIdetifier(nextTokenTRUE, showErrorTRUE) ){
            error = true;
            errorLog.add("Ожидается идентификатор", parseTokenList.get(i));
        }
        //Ожидаем открывающую скобку или точку с запятой (identifer1 {,identiferN}*); or ;
        currentBlockBracketAndIdentifer(nextTokenTRUE,showErrorTRUE);
        return !error;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Служебные функции">
    public String parse(ArrayList<TokenParser> ParseTokenList) {
        init(ParseTokenList);
        String result = "";
        TokenParser blockProgram = new TokenParser( "%globalblock%", BLOCK );
        parseTree = new Tree<TokenParser>( blockProgram );
        currentList = parseTree;
        addLeafBlock( "%BlockProgram%" );
        currentBlockProgram();
        addLeafBlock( parseTree ,"%BlockGlobalDeclarations%" );
        currentBlockGlobalDeclarations(nextTokenTRUE, showErrorTRUE, currentList);
           
        CodeGenarator codeGenerator = new CodeGenarator(parseTree, tokenList);

        result = result.concat(codeGenerator.getCText());
        //System.out.println(parseTree.toString());
        return result;

    }
    
    private void nextToken(){
        currentList.addLeaf(parseTokenList.get(i));
        i++;
    }
    
    private void backToken(){
        i--;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Работа с деревом разбора">
    //Добавляет в определенный узел дерева и изменяет currentList
    private Tree<TokenParser> addLeafBlock(Tree<TokenParser> CurrentList, String TextBlock){
        TokenParser programDeclaration = new TokenParser( TextBlock , BLOCK );
        return currentList = CurrentList.addLeaf(programDeclaration);
    }
    //
    private void addLeafBlockToList(Tree<TokenParser> CurrentList, String TextBlock){
        TokenParser programDeclaration = new TokenParser( TextBlock , BLOCK );
        currentList = CurrentList.addLeaf(programDeclaration);
    }
    //Добавляет в текущий узел дерева
    private void addLeafBlock(String TextBlock){
        TokenParser programDeclaration = new TokenParser( TextBlock , BLOCK );
        currentList = currentList.addLeaf(programDeclaration);
    }// </editor-fold>
}

// <editor-fold defaultstate="collapsed" desc="Класс ошибок">
class ErrorLog{
    String ErrorLogStr = "";
    javax.swing.JTextArea jTextErrorWiget;
    ;
    public ErrorLog( javax.swing.JTextArea jTextError ){
        jTextErrorWiget = jTextError;
    }
    
    public void setWiget(  javax.swing.JTextArea jTextError ){
        jTextError.setText(null);
        jTextErrorWiget = jTextError;
    }
    public void add(String error, TokenParser token){
        ErrorLogStr = ErrorLogStr.concat("Ошибка в строке:");
        ErrorLogStr = ErrorLogStr.concat(String.valueOf(token.getLine()));
        ErrorLogStr = ErrorLogStr.concat(" символ:");
        ErrorLogStr = ErrorLogStr.concat(String.valueOf(token.getSymbol()));
        ErrorLogStr = ErrorLogStr.concat(" ".concat(error));
        ErrorLogStr = ErrorLogStr.concat("\n");
        jTextErrorWiget.setText( ErrorLogStr );
    }
    
    public void add(TokenParser token){
        ErrorLogStr = ErrorLogStr.concat("Ошибка в строке:");
        ErrorLogStr = ErrorLogStr.concat(String.valueOf(token.getLine()));
        ErrorLogStr = ErrorLogStr.concat(" символ:");
        ErrorLogStr = ErrorLogStr.concat(String.valueOf(token.getSymbol()));
        ErrorLogStr = ErrorLogStr.concat(" ".concat("Ожидается ".concat(token.getText())));
        ErrorLogStr = ErrorLogStr.concat("\n");
        jTextErrorWiget.setText( ErrorLogStr );
    }
    public String get(){
        return ErrorLogStr;
    }
    
}// </editor-fold>