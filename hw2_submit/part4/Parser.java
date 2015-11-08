/* *** This file is given as part of the programming assignment. *** */

public class Parser {
	
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private void scan() {
        tok = scanner.scan();
    }

    private Scan scanner;
    private CodeWriter writer;
    private SymTabStack stack;
    Parser(Scan scanner, CodeWriter writer) {
        this.scanner = scanner;
        this.writer = writer;
        stack = new SymTabStack();
        scan();
        program();
        if( tok.kind != TK.EOF )
            parse_error("junk after logical end of program");
    }

    private void program() {
    	writer.write("#include <stdio.h>\n" +
    			"main()\n");
        block();
        writer.done();
    }

    private void block(){
    	stack.newBlock();
        
    	writer.writeLn("{");
    	
    	declaration_list();
        statement_list();
        
        writer.writeLn("}");
        stack.leaveBlock();
    }

    private void declaration_list() {
        // below checks whether tok is in first set of declaration.
        // here, that's easy since there's only one token kind in the set.
        // in other places, though, there might be more.
        // so, you might want to write a general function to handle that.
        while( is(TK.DECLARE) ) {
            declaration();
        }
    }

    private void declaration() {
        mustbe(TK.DECLARE);                
        shouldNotDuplicateID(TK.ID);//mustbe(TK.ID);                
        while( is(TK.COMMA) ) {
            scan();            
            shouldNotDuplicateID(TK.ID);//mustbe(TK.ID);
        }
    }

    private void statement_list(){
    	while(is(TK.ID)||
    			is(TK.LEVEL_REF)||
    			is(TK.PRINT)||
    			is(TK.DO)||
    			is(TK.IF))
    	{
    		statement();
    	}
    }
    private void statement(){
    	
		if (is(TK.ID) || is(TK.LEVEL_REF)){//assignment
			assignment();
		}else if(is(TK.PRINT)){
			print();
		}else if(is(TK.DO)){
			doo();
		}else if(is(TK.IF)){
			iif();
		}
    }

	private void assignment() {
		ref_id();
		mustbe(TK.ASSIGN);
		writer.write("=");		//code generation
		expr();
		writer.writeLn(";");		//code generation
	}

    private void expr() {
    	term();
    	while(is(TK.PLUS) || is(TK.MINUS)){
    		addop();
    		term();
    	}
	}

	private void addop() {
		if(is(TK.PLUS)){
			writer.write("+");
			scan();
		}else if(is(TK.MINUS)){
			writer.write("-");
			scan();
		}
	}

	private void term() {
		factor();
		while(is(TK.TIMES) || is(TK.DIVIDE)){
			mulop();
			factor();
		}
	}

	private void mulop() {
		if(is(TK.TIMES)){
			writer.write("*");
			scan();
		}else if(is(TK.DIVIDE)){
			writer.write("/");
			scan();
		}
	}

	private void factor() {
		
		if(is(TK.LPAREN)){
			writer.write("(");
			scan();
			expr();
			mustbe(TK.RPAREN);
			writer.write(")");
		}else if(is(TK.LEVEL_REF) || is(TK.ID)){
			ref_id();
		}else if(is(TK.NUM)){
			writer.write(tok.string);
			scan();
		}else{
			parse_error("Illegal input @ line:" + tok.lineNumber);
		}
		
	}

	private void ref_id() {
		
		boolean hasLevel = false;
		int level = 0;
		
		if(is(TK.LEVEL_REF)){
			hasLevel = true;
			level = SymTabStack.DEFAULT_CURRENT_BLOCK;
			scan();
			if(is(TK.NUM)){
				level = Integer.parseInt(tok.string);
				scan();
			}
		}
		
		if(hasLevel){
			mustNotUnDeclaredIDLevel(level,TK.ID);	
		}else{
			mustNotUnDeclaredID(TK.ID);
		}		
				
	}

	private void iif() {
		mustbe(TK.IF);
		writer.write("if ");
		guarded_command();
		while(is(TK.ELSEIF)){
			writer.write("else if");
			scan();
			guarded_command();	
		}
		if(is(TK.ELSE)){
			writer.write("else ");
			scan();
			block();
		}
		mustbe(TK.ENDIF);
	}

	private void doo() {
		mustbe(TK.DO);
		writer.write("while");
		guarded_command();
		mustbe(TK.ENDDO);
	}

	private void guarded_command() {
		writer.write("(");
		expr();
		writer.writeLn(" <= 0)");
		mustbe(TK.THEN);
		block();
	}

	private void print() {
		mustbe(TK.PRINT);
		writer.write("printf(\"%d\\n\",");
		expr();
		writer.writeLn(");");
	}

	// is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
        if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (mustbe)" );
        }
        scan();
    }
    
    private void shouldNotDuplicateID(TK tk){
        
    	if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (shouldNotDuplicateID)" );
        }
    	if(stack.hasVarCurrentScope(tok.string)){    		
    		System.err.println("redeclaration of variable " + tok.string);
            //System.err.println(tok.string + " is redeclared variable at line" + tok.lineNumber);    		
    	}else{
    		stack.addVar(tok.string);
    		String varFullName = stack.getFullVarName(tok.string);
    		writer.writeLn("int " + varFullName + ";");
    	}
        scan();    	
    }
    
    private void mustNotUnDeclaredID(TK tk){
    	if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (mustNotUnDeclaredID)" );
        }
    	if(!stack.hasVar(tok.string)){
    		semantic_error(tok.string + " is an undeclared variable on line " + tok.lineNumber);
    	}else{
    		String varFullName = stack.getFullVarName(tok.string);	//for code generation
    		writer.write(varFullName);							//for code generation
    	}
        scan();
    }
    
    private void mustNotUnDeclaredIDLevel(int level, TK tk){
    	if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (mustNotUnDeclaredID)" );
        }
    	if(!stack.hasVarAtLevel(level, tok.string)){
    		String varName = "~" + (level == SymTabStack.DEFAULT_CURRENT_BLOCK?"":String.valueOf(level)) + tok.string; 
    		semantic_error("no such variable " + varName + " on line " + tok.lineNumber);
    	}else{
    		String varFullName = stack.getFullVarNameAtLevel(level, tok.string);//for code generation
    		writer.write(varFullName);				//for code generation
    	}
        scan();
    	
    }

    private void semantic_error(String msg){
        System.err.println(msg);
        System.exit(1);    	
    }
    
    private void parse_error(String msg) {
        System.err.println( "can't parse: line "
                            + tok.lineNumber + " " + msg );
        System.exit(1);
    }
}
