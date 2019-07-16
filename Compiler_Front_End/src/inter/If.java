package inter;

import main.CompilerPhase;
import symbols.Type;

public class If extends Stmt {
	
	Expr expr;Stmt stmt;
	public If(Expr x,Stmt s) {
		expr=x; stmt=s;
		if( expr.type != Type.bool ) expr.error("boolean required in if");
	}
	public void gen(int b, int a) {
		int label = newlable();
		expr.jumping(0, a);
		emitlabel(label);
		stmt.gen(label, a);
	}

}
