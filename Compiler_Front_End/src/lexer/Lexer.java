package lexer;import main.*;
import java.io.*;
import java.util.*;

import symbols.*;
public class Lexer {

	public static int line=0;
	char peek=' ';int asd=-1;int len=0;
	//File fl=CompilerPhase.selectedFile;
	//FileInputStream fn=null;
	String str=CompilerPhase.mytext1.getText().toString();
	int ssd=-1;
	Hashtable words=new Hashtable();
	void reserve(Word w) {words.put(w.lexeme, w);
	}
	public Lexer() {
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);reserve(Word.False);
		reserve(Type.Int);reserve(Type.Char);
		reserve(Type.bool);reserve(Type.Float);
	}
	/*public Lexer(String str) {
		file=new File(str);
	}*/
	void readch() throws IOException {
		//fn=new FileInputStream(fl);
		//BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		//byte fc[]=new byte[(int)fl.length()];
		char[] strArray=str.toCharArray();
		//len=fn.read(fc);
		if(asd<strArray.length-2)
		{
			asd++;
		}
		peek=strArray[asd];
	}
	boolean readch(char c) throws IOException{
		readch();
		if(peek!=c) return false;
		peek=' ';
		return true;
	}
	
	public Token scan() throws IOException{
		for(;;readch()) {
			if(peek==' '||peek=='\t') continue;
			else if(peek=='\n')line=line+1;
			else break;
		}
		switch(peek) {
		case '&':
			if(readch('&')) return Word.and; else return new Token('&');
		case '|':
			if(readch('|')) return Word.or; else return new Token('|');
		case '=':
			if(readch('=')) return Word.eq; else return new Token('=');
		case '!':
			if(readch('=')) return Word.ne; else return new Token('!');
		case '<':
			if(readch('=')) return Word.le; else return new Token('<');
		case '>':
			if(readch('=')) return Word.ge; else return new Token('>');
		}
		if(Character.isDigit(peek)) {
			int v=0;
			do {
				v=10*v + Character.digit(peek, 10);readch();
			}while(Character.isDigit(peek));
			if(peek!='.') return new Num(v);
			float x=v;float d=10;
			for(;;) {
				readch();
				if(!Character.isDigit(peek)) break;
				x=x+Character.digit(peek, 10)/d;d=d*10;
			}
			return new Real(x);
		}
		if(Character.isLetter(peek)) {
			StringBuffer b=new StringBuffer();
			do {
				b.append(peek);readch();
			}while(Character.isLetterOrDigit(peek));
			String s=b.toString();
			Word w=(Word)words.get(s);
			if(w!=null) return w;
			w=new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		Token tok=new Token(peek);
		peek=' ';
		return tok;
	}
	
}
