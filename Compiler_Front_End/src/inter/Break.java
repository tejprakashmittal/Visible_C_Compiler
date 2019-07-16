package inter;

public class Break extends Stmt {
	Stmt stmt;
	public Break() {
		if(Stmt.Enclosing==Stmt.Null) error("unenclosed break");
	}
	public void gen(int b,int a)
	{
		emit(" goto L"+stmt.after);
		System.out.println();
	}

}
