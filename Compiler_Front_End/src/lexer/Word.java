package lexer;

import main.CompilerPhase;

public class Word extends Token {

	public String lexeme="";
	public Word(String s,int tag) {
		super(tag);
		lexeme=s;
	}
	public String toString() {
		return lexeme;
	}
	public static final Word and=new Word("&&", Tag.AND),
			or =new Word("||", Tag.OR),
			eq =new Word("==", Tag.EQ),
			le =new Word("<=", Tag.LE),
			ne=new Word("!=", Tag.NE),
			ge=new Word(">=", Tag.GE),
			minus=new Word("minus", Tag.MINUS),
			True=new Word("true", Tag.TRUE),
			False=new Word("false", Tag.FALSE),
			temp=new Word("t", Tag.TEMP);

}
