import java.util.ArrayList;

/*
 * This class represents a block in the blocks' stack
 * An instance of this class maintains the list of block's variable   
 */
public class SymTab {

	//The relative nesting level of the block in the program.
	private int level;
	//Prefix added to the name of the variable when it is generated as C code.
	private static final String VAR_PREFIX = "_e";
	//List of variable names declared in the block.
	ArrayList<String> varNames;

	public SymTab(int level){
		this.level = level;
		varNames = new ArrayList<String>();
	}
	
	public int getLevel(){
		return level;
	}
	
	//add a new variable into block. 
	public boolean putVar(String varName){
		if (varNames.contains(varName)){
			return false;
		}else{
			varNames.add(varName);
			return true;
		}
	}
	
	//check if the block has a variable with given name.
	public boolean hasVar(String varName){
		return varNames.contains(varName);
	}
	
	//get the full name of the variable in block.
	//full name has the format: Prefix + level + name of variable.
	public String getVarFullName(String varName){

		if (varNames.contains(varName)){
			return VAR_PREFIX + String.valueOf(level) + "_" + varName;			
		}else{
			return null;
		}		
		
	}
}
