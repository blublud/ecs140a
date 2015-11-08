public class e2c {
 
    public static void main(String args[]) {

    	Scan scanner = new Scan(args);
        CodeWriter writer = new CodeWriter();
        new Parser(scanner, writer);

    }
}