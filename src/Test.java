import java.util.List;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("test started ...");
		
		Calculator c = new Calculator();
		
		String expression = "(21*31+(41+51)*(61+7)*81+91) +1000";
		// String expression = "(2+3)*(4+5)";
		
		try {
			List<Token> rpnList = c.convertToRPN(expression);
			displayStuff(rpnList);
			
			System.out.println(c.calculate(expression));
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		System.out.println("test finished ...");
	}

	public static void displayStuff(List<Token> tokens) {
		for(Token t: tokens) {
			System.out.println(t.display());
		}
	}
}
