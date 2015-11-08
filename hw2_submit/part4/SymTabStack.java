import java.util.ArrayList;


public class SymTabStack {

	public static final int DEFAULT_CURRENT_BLOCK = Integer.MIN_VALUE;
	
	ArrayList<SymTab> stack;
	
	public SymTabStack(){
		stack = new ArrayList<SymTab>();
		
	}
	
	public SymTab newBlock(){
		if (stack.size() == 0){
			stack.add(new SymTab(0));		//Symbol table for global scope	
		}else{
			int currentLevel = stack.get(stack.size() - 1).getLevel();
			stack.add(new SymTab(currentLevel + 1));			
		}
		return stack.get(stack.size() -1);		
	}
	
	public void leaveBlock(){
		//necessary to check if leaving global block?
		stack.remove(stack.size() -1);
	}
	
	public SymTab getCurrentBlock(){
		return stack.get(stack.size() -1);
	}
	
	public int getGlobalBlockLevel(){
		return getCurrentBlock().getLevel();
	}
	
	public boolean addVar(String varName){
		return getCurrentBlock().putVar(varName);
	}
	
	public boolean hasVar(String varName){
		int i = stack.size() -1;
		while(i >=0 && !stack.get(i).hasVar(varName)){
			i--;
		}		
		return (i >= 0);
	}

	public boolean hasVarCurrentScope(String varName){
		int i = stack.size() -1;
		return stack.get(i).hasVar(varName);
	}

	public boolean hasVarAtLevel(int level, String varName){
		level = convertToStackLevel(level);
		if (level < 0 || level >= stack.size()){
			return false;
		}
		return stack.get(level).hasVar(varName);
	}
	
	public String getFullVarName(String varName){

		int i = stack.size() -1;
		while(i >=0 && !stack.get(i).hasVar(varName)){
			i--;
		}		
		if (i>=0){
			return stack.get(i).getVarFullName(varName);
		}
		
		return null;
	}

	private int convertToStackLevel(int level){
		if (level == DEFAULT_CURRENT_BLOCK){
			level = getCurrentBlock().getLevel();
		}
		return getCurrentBlock().getLevel() - level;		
	}
	
	public String getFullVarNameAtLevel(int level, String varName){
		level = convertToStackLevel(level);
		if (level < 0 || level >= stack.size()){
			return null;
		}
		return stack.get(level).getVarFullName(varName);
	}
}
