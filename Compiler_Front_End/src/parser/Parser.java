package parser;
import java.awt.Color;
import java.io.*;import lexer.*;
import main.CompilerPhase;
import symbols.*;import inter.*;
public class Parser {
	String[] lexP=new String[30];
	int k=0;int pr=0;
	private Lexer lex;
	private Token look;
	Env top=null;
	int used=0;
	public Parser(Lexer l) throws IOException{lex=l;move();}
	void move() throws IOException {look=lex.scan();}
	void error(String s) {CompilerPhase.mytext2.append("\tError\n"+"\n near line "+Lexer.line+" : "+s);
	}
	void match(int t) throws IOException{
		if(look.tag==t) move();
		else error("syntax error  "+"'"+(char)t+"'"+" Missing");
	}
	
	/*public void printSymbol() {
		Env env=new Env(n);
	}*/
	
	public void program() throws IOException{
		Stmt s=block();
		int begin=s.newlable();int after=s.newlable();
		s.emitlabel(begin);s.gen(begin, after);s.emitlabel(after);
	}
	Stmt block() throws IOException {
		match('{'); Env savedEnv = top; top=new Env(top);
		decls(); Stmt s=stmts();
		match('}'); top=savedEnv;
		return s;
	}
	
	void decls() throws IOException {
		while(look.tag==Tag.BASIC) {
			Type p=type(); Token tok = look; match(Tag.ID); match(';');
			Id id=new Id((Word)tok,p,used);
			//CompilerPhase.mytext2.append("\tSymbol Table\n");
			top.put(tok,id);
			used=used+p.width;
			if(p.width==4)
				CompilerPhase.mytext6.append("\n\t"+id+"\t"+"Int");
			else if(p.width==8)
			    CompilerPhase.mytext6.append("\n\t"+id+"\t"+"Float");
			else if(p.width==1)
				CompilerPhase.mytext6.append("\n\t"+id+"\t"+"Char");
				
		}
	}
	Type type() throws IOException {
		Type p=(Type)look;
		match(Tag.BASIC);
		if(look.tag!='[') return p;
		else return dims(p);
	}
	Type dims(Type p) throws IOException{
		match('['); Token tok=look; match(Tag.NUM); match(']');
		if(look.tag == '[')
			p = dims(p);
		return new Array(((Num)tok).value,p);
	}
	
	Stmt stmts() throws IOException {
		if(look.tag=='}') return Stmt.Null;
		else return new Seq(stmt(),stmts());
	}
	
	Stmt stmt() throws IOException{
		Expr x; Stmt s,s1,s2;
		Stmt savedStmt;
		
		switch(look.tag)
		{
		case ';':
			move();
			return Stmt.Null;
		case Tag.IF:
			CompilerPhase.mytext4.append("\n\n\t if \n\t/  \\");
			match(Tag.IF);; match('('); x=bool(); match(')');
			s1=stmt();
			if(look.tag != Tag.ELSE) return new If(x,s1);
			match(Tag.ELSE);
			s2=stmt();
			return new Else(x,s1,s2);
		case Tag.WHILE:
			CompilerPhase.mytext4.append("\n\n\twhile \n\t/  \\");
			While whilenode=new While();
			savedStmt=Stmt.Enclosing; Stmt.Enclosing=whilenode;
			match(Tag.WHILE); match('('); x=bool(); match(')');
			s1=stmt();
			whilenode.init(x, s1);
			Stmt.Enclosing=savedStmt;
			return whilenode;
		case Tag.DO:
			Do donode=new Do();
			savedStmt=Stmt.Enclosing; Stmt.Enclosing=donode;
			match(Tag.DO);
			s1=stmt();
			match(Tag.WHILE);
			match('(');x=bool(); match(')'); match(';');
			donode.init(x, s1);
			Stmt.Enclosing=savedStmt;
			return donode;
		case Tag.BREAK:
			match(Tag.BREAK);match(';');
			return new Break();
		case '{':
			return block();
		default:
			return assign();
		}
	}

	Stmt assign() throws IOException{
		Stmt stmt; Token t=look;
		match(Tag.ID);
		Id id=top.get(t);
		if(id == null) {error("'"+t.toString()+"'"+ " undeclared");};
		if(look.tag == '=') {
			if(pr==0)
			{
				pr++;
				CompilerPhase.mytext3.append("\n\t"+(char)look.tag+"\t"+"Operator");
			CompilerPhase.mytext4.append("   =\n                     /  \\  /  \\\n                   "+lexP[0]+"   "+lexP[1]+"   "+lexP[0]+"   "+lexP[1]);
			
			}
			move(); stmt = new Set(id,bool());
		}
		else {
			Access x=offset(id);
			match('='); stmt = new SetElem(x,bool());
		}
		match(';');
		return stmt;
	}
	
	Expr bool() throws IOException{
		Expr x=join();
		while(look.tag == Tag.OR)
		{
			CompilerPhase.mytext3.append("\n\t||\t"+"Operator");
			Token tok=look; move(); x=new Or(tok,x,join());
		}
		return x;
	}
	Expr join() throws IOException
	{
		Expr x=equality();
		while(look.tag == Tag.AND)
		{
			CompilerPhase.mytext3.append("\n\t&&\t"+"Operator");
			Token tok=look; move(); x=new And(tok,x,equality());
		}
		return x;
	}
	Expr equality() throws IOException{
		Expr x=rel();
		while( look.tag == Tag.EQ || look.tag == Tag.NE) {
			CompilerPhase.mytext3.append("\n\t"+(char)look.tag+"\t"+"Operator");
			Token tok=look; move(); x= new Rel(tok,x,rel());
		}
		return x;
	}
	Expr rel() throws IOException{
		Expr x=expr();
		
		switch(look.tag) {
		case '<': case Tag.LE:CompilerPhase.mytext3.append("\n\t"+(char)look.tag+"\t"+"Operator"); case Tag.GE: case '>':CompilerPhase.mytext4.append("\n                      >");CompilerPhase.mytext3.append("\n\t"+(char)look.tag+"\t"+"Operator");
			Token tok=look; move();return new Rel(tok,x,expr());
			default:
				return x;
		}
	}
	Expr expr() throws IOException{
		Expr x=term();
		while(look.tag == '+' || look.tag == '-') {
			CompilerPhase.mytext3.append("\n\t"+(char)look.tag+"\t"+"Operator");
			Token tok=look; move(); x= new Arith(tok,x,term());
		}
		return x;
	}
	Expr term() throws IOException{
		Expr x=unary();
		while(look.tag == '*' || look.tag == '/') {
			CompilerPhase.mytext3.append("\n\t"+(char)look.tag+"\t"+"Operator");
			Token tok=look; move(); x=new Arith(tok,x,unary());
		}
		return x;
	}
	Expr unary() throws IOException{
		if(look.tag == '-') {
			move(); return new Unary(Word.minus,unary());
		}
		else if(look.tag == '!') {
			Token tok=look; move(); return new Not(tok,unary());
		}
		else return factor();
	}
	
	Expr factor() throws IOException{
		Expr x=null;
		switch(look.tag) {
		case '(':
			move(); x=bool();match(')');
			return x;
		case Tag.NUM:
			x=new Constant(look, Type.Int);
		case Tag.REAL:
			x=new Constant(look, Type.Float);
		case Tag.TRUE:
			x=Constant.True;
		case Tag.FALSE:
			x=Constant.False;
		default:
			error("syntax error");
			return x;
		case Tag.ID:
			String s=look.toString();
			Id id=top.get(look);
			int ii;
            for(ii=0;ii<lexP.length;ii++)	{
            	if(lexP[ii]==s) {
            		break;
            	}
            }
            if(ii==lexP.length) {
            	lexP[k]=s;
            	k++;
            	CompilerPhase.mytext3.append("\n\t"+id+"\t"+"Identifier");
            }
			if(id == null) {Lexer.line++;error(look.toString()+" undeclared");};
			move();
			if(look.tag != '[') return id;
			else return offset(id);
		}
	}
	Access offset(Id a) throws IOException{
		Expr i,w,t1,t2,loc;
		Type type=a.type;
		match('['); i=bool(); match(']');
		type=((Array)type).of;
		w = new Constant(type.width);
		t1=new Arith(new Token('*'),i,w);
		loc=t1;
		while(look.tag == '[') {
			 match('['); i=bool();match(']');
			 type=((Array)type).of;
			 w = new Constant(type.width);
			 t1=new Arith(new Token('*'),i,w);
			 t2=new Arith(new Token('+'),loc,t1);
			 loc=t2;
		}
		return new Access(a,loc,type);
	}
}