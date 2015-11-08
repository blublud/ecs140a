import java.util.ArrayList;


public class SymTab {

	private int level;
	private static final String VAR_PREFIX = "_e";
	ArrayList<String> varNames;
		
	public SymTab(int level){
		this.level = level;
		varNames = new ArrayList<String>();
	}
	
	public int getLevel(){
		return level;
	}
	
	public boolean putVar(String varName){
		if (varNames.contains(varName)){
			return false;
		}else{
			varNames.add(varName);
			return true;
		}
	}
	
	public boolean hasVar(String varName){
		return varNames.contains(varName);
	}
	
	public String getVarFullName(String varName){

		if (varNames.contains(varName)){
			return VAR_PREFIX + String.valueOf(level) + "_" + varName;			
		}else{
			return null;
		}		
		
	}
}
