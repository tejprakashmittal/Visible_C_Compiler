package inter;import lexer.*;import main.*;

public class Node {
int lexline=0;
CompilerPhase obj1=new CompilerPhase();
Node(){lexline=Lexer.line;}
void error(String s) {CompilerPhase.mytext2.append("near line "+lexline+":"+s+"\n");}
static int labels=0;
public int newlable() {return ++labels;}
public void emitlabel(int i) {CompilerPhase.mytext5.append(CompilerPhase.newLine+" L"+i+":");
	System.out.print(" L"+i+":");}
public void emit(String s) {CompilerPhase.mytext5.append("  "+s);
	}
}
