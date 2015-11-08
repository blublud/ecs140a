import java.util.ArrayList;

public class Test{
	
	public static void main(String[] args){
		ArrayList<String> l = new ArrayList<String>();
		l.add("1");l.add("2");
		for (String s: l){
			System.out.println(s);
		}
		
		for (int i=0; i < l.size();i++){
			System.out.println(l.get(i));
		}
	}
}