import java.io.PrintWriter;

/*
 * This class is used to write the generated C code into an output stream.
 * In this case, the output is std output.
 */
public class CodeWriter {

	private PrintWriter writer;
	public CodeWriter(){
		writer = new PrintWriter(System.out);
	}
	
	public void write(String content){
		writer.print(content);
	}
	
	public void writeLn(String content){
		writer.println(content);
	}
	
	public void newLine(){
		writer.println("");
	}
	
	public void done(){
		writer.flush();
	}
	
//    private void codeGenErr(String msg) {
//        System.err.println( msg);
//        System.exit(1);
//    }

}
