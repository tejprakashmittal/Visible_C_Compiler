package symbols;
import java.util.*;import lexer.*;import inter.*;
public class Env {
	protected Env prev;
private Hashtable table;
public Env(Env n) {table=new Hashtable();prev=n;}

public void put(Token w,Id i) {
	table.put(w, i);
}
public Id get(Token w) {
	for(Env e=this;e!=null;e=e.prev) {
		Id found=(Id)(e.table.get(w));
		if(found!=null) return found;
	}
	return null;
}
}