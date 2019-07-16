package inter;
import symbols.*;
import symbols.Type;

public class Do extends Stmt {
	Expr expr;Stmt stmt;
	public Do() { expr = null;stmt = null; }
	public void init(Expr x,Stmt s) {
		expr = x;stmt=s;
		if( expr.type != Type.bool ) expr.error("boolean required in do");
	}
	public void gen(int b,int a) {
		after = a;
		int label=newlable();
		stmt.gen(b,label);
		emitlabel(label);
		expr.jumping(b, 0);
	}
}
