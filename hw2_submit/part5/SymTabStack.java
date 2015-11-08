import java.util.ArrayList;

/*
 * This class represent the stack of blocks' variable.
 */
public class SymTabStack {

	//Reserved variable used to refer to current block. 
	public static final int DEFAULT_CURRENT_BLOCK = Integer.MIN_VALUE;
	
	//stack of the blocks's variables.
	ArrayList<SymTab> stack;
	
	public SymTabStack(){
		stack = new ArrayList<SymTab>();
		
	}
	
	//push new entry into the stack when enterring a new block.
	public SymTab newBlock(){
		if (stack.size() == 0){
			stack.add(new SymTab(0));		//Symbol table for global scope	
		}else{
			int currentLevel = stack.get(stack.size() - 1).getLevel();
			stack.add(new SymTab(currentLevel + 1));			
		}
		return stack.get(stack.size() -1);		
	}
	
	//pop out the top entry when leaving a block.
	public void leaveBlock(){
		//necessary to check if leaving global block?
		stack.remove(stack.size() -1);
	}
	
	//get the entry at the top of the stack
	public SymTab getCurrentBlock(){
		return stack.get(stack.size() -1);
	}
	
	//get the level number of the top entry in the stack. 
	public int getGlobalBlockLevel(){
		return getCurrentBlock().getLevel();
	}
	
	//add a new variable into the top entry of the stack.
	public boolean addVar(String varName){
		return getCurrentBlock().putVar(varName);
	}
	
	//check if the stack has a variable with the given name
	//the order of checking is from the top entry (current block)
	//to the the bottom entry (global block).
	public boolean hasVar(String varName){
		int i = stack.size() -1;
		while(i >=0 && !stack.get(i).hasVar(varName)){
			i--;
		}		
		return (i >= 0);
	}

	//check if the top entry (current block) has a variable with the given name
	public boolean hasVarCurrentScope(String varName){
		int i = stack.size() -1;
		return stack.get(i).hasVar(varName);
	}

	//check if a specific entry (specific block) has a variable with the given name. 
	public boolean hasVarAtLevel(int level, String varName){
		level = convertToStackLevel(level);
		if (level < 0 || level >= stack.size()){
			return false;
		}
		return stack.get(level).hasVar(varName);
	}
	
	//finding a variable with the given name
	//the order of finding is from the top entry (current block)
	//to the the bottom entry (global block).
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

	//convert a number specifying a level in E language into
	//the number represented the associated level in the internal blocks' stack
	private int convertToStackLevel(int level){
		if (level == DEFAULT_CURRENT_BLOCK){
			level = getCurrentBlock().getLevel();
		}
		return getCurrentBlock().getLevel() - level;		
	}
	
	//finding a variable with the given name at a specific level
	//the order of finding is from the top entry (current block)
	//to the the bottom entry (global block).
	public String getFullVarNameAtLevel(int level, String varName){
		level = convertToStackLevel(level);
		if (level < 0 || level >= stack.size()){
			return null;
		}
		return stack.get(level).getVarFullName(varName);
	}
}
