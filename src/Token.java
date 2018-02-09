
public class Token {
	public static enum TokenType {
		number,
		operator;
		
		public boolean isNumber() {
			return this.equals(TokenType.number);
		}
	}
	
	public static enum Operator {
		add('+', 1),
		subtract('-', 1),
		multiply('*', 2),
		divide('/', 2),
		leftBracket('(', 3),
		rightBracket(')', 3);
		
		private char displayChar;
		private int precedence; 
		private Operator(char displayChar, int precedence) {
			this.displayChar = displayChar;
			this.precedence = precedence;
		}
		
		public boolean isArithmeticOperator() {
			return
				this.equals(Operator.add) ||
				this.equals(Operator.subtract) ||
				this.equals(Operator.multiply) ||
				this.equals(Operator.divide);
		}
		
		public boolean isLeftBracket() {
			return this.equals(Operator.leftBracket);
		}
		
		public boolean isRightBracket() {
			return this.equals(Operator.rightBracket);
		}
		
		public boolean greaterPrecedenceThan(Operator otherOp) {
			return this.precedence > otherOp.precedence;
		}

		public boolean eqPrecedence(Operator otherOp) {
			return this.precedence == otherOp.precedence;
		}

		public boolean isLeftAssociative() {
			return true;
		}

		public char display() {
			return displayChar;
		}
		
	}
	
	protected int number;
	protected TokenType tokenType;
	protected Operator operator;
	
	public Token(int num) {
		this.tokenType = TokenType.number;
		this.number = num;
		this.operator = null;
	}
	
	public Token(Operator op) {
		this.tokenType = TokenType.operator;
		this.number = 0;
		this.operator = op;
	}
	
	public String display() {
		if (tokenType.equals(TokenType.number)) {
			return Integer.toString(number);
		} else if (tokenType.equals(TokenType.operator)) {
			return "" + operator.display();
		} else {
			return "<UNKNOWN TOKEN TYPE>";
		}
	}
	
	public boolean isNumber() {
		return this.tokenType.isNumber();
	}
	
	public boolean isArithmeticOperator() {
		if (this.operator == null) {
			return false;
		}
		
		return this.operator.isArithmeticOperator();
	}
	
	public boolean isLeftBracket() {
		if (this.operator == null) {
			return false;
		}
		
		return this.operator.isLeftBracket();
	}

	public boolean isRightBracket() {
		if (this.operator == null) {
			return false;
		}
		
		return this.operator.isRightBracket();
	}

	public static Token parseChar(char c) throws IllegalArgumentException {
		if (c == '(') {
			return new Token(Token.Operator.leftBracket);
		} else if (c == ')') {
			return new Token(Token.Operator.rightBracket);
		} else if (c == '*') {
			return new Token(Token.Operator.multiply);
		} else if (c == '/') {
			return new Token(Token.Operator.divide);
		} else if (c == '+') {
			return new Token(Token.Operator.add);
		} else if (c == '-') {
			return new Token(Token.Operator.subtract);
		} else {
			throw new IllegalArgumentException("Can not parse '" + c + "' as a token.");
		}
	}
}
