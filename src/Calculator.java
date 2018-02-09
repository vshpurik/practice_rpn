import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Calculator {

	private Stack<Token> operators = new Stack<>();
	private List<Token> values = new LinkedList<>();
	
	public int calculate(String expression) throws Exception {
		List<Token> rpn = convertToRPN(expression);
		return calculateRPN(rpn);
	}
	
	public int calculateRPN(List<Token> rpn) throws Exception {
		Stack<Integer> tmp = new Stack<>();
		
		for(Token t: rpn) {
			if (t.isNumber()) {
				tmp.push(t.number);
			} else if (t.isArithmeticOperator()) {
				int var2 = tmp.pop();
				int var1 = tmp.pop();
				switch (t.operator) {
				case add:
					tmp.push(var1 + var2);
					break;
				case subtract:
					tmp.push(var1 - var2);
					break;
				case multiply:
					tmp.push(var1 * var2);
					break;
				case divide:
					tmp.push(var1 / var2);
					break;
				default:
					throw new Exception("Unknown operator " + t.operator.display());
				}
						
			} else {
				throw new Exception("Unexpected token in RPN: " + t.display());
			}
		}
		
		return tmp.pop();
	}
	
	public List<Token> convertToRPN(String expression) throws Exception {
		LinkedList<Token> tokens = tokenize(expression);
		
		while(!tokens.isEmpty()) {
			Token t = tokens.remove();
			if (t.isNumber()) {
				values.add(t);
			
			} else if (t.isArithmeticOperator()) {
				while (!operators.isEmpty() && keepPopping(t, operators.peek())) {
					values.add(operators.pop());
				}
				operators.push(t);
			
			} else if (t.isLeftBracket()) {
				operators.push(t);

			} else if (t.isRightBracket()) {
				Token topT = operators.pop();
				while (!topT.isLeftBracket()) {
					values.add(topT);
					topT = operators.pop();
				}
				
				if (!topT.isLeftBracket()) {
					throw new Exception("Mismatched brackets");
				}
			}
		}
		
		while (!operators.isEmpty()) {
			values.add(operators.pop());
		}
		
		return values;
	}

	private boolean keepPopping(Token inputTok, Token topStackTok) {
		return
			!topStackTok.isLeftBracket() && topStackTok.operator.greaterPrecedenceThan(inputTok.operator) ||
			topStackTok.operator.eqPrecedence(inputTok.operator) && inputTok.operator.isLeftAssociative();
	}
	
	private LinkedList<Token> tokenize(String exp) {
		LinkedList<Token> result = new LinkedList<>();

		// remove all white space
		exp = exp.replaceAll("\\s","");

		if (exp.isEmpty()) {
			return result;
		}
		
		int i=-1;
		int maxI = exp.length() - 1;
		while (i<maxI) {
			i++;
			
			char c = exp.charAt(i);
			
			if (c >= '0' && c <= '9') {
				// find substring that represents this number
				int startPos = i;
				int endPos = i + 1;
				while (exp.length() - i > 1 && exp.charAt(i+1) >= '0' && exp.charAt(i+1) <= '9') {
					++i;
					++ endPos;
				}

				int num = Integer.parseInt(exp.substring(startPos, endPos));
				result.add(new Token(num));
			} else {
				result.add(Token.parseChar(c));
			}
		}
		
		return result;
	}
}
