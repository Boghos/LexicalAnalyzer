
import java.util.*;

public class lexicalAnalizer {
    
    	 // Main method to run the lexical analyzer on a sample input
    	   public static void main(String[] args) {
    	       String text = "int x = 7; float y = 3.5; String name = \"John\"; if (x < y) { x = x + 5; } name = 5; 1abc = 10; @test";
    	       analyze(text);
    	   }

    		    // Keywords, operators, and separators
    		    private static final String[] KEYWORDS = {"if", "else", "return", "int", "float", "String", "Boolean"};
    		    private static final String[] OPERATORS = {"+", "-", "*", "/", "=", "==", "!=", ">", "<"};
    		    private static final char[] SEPARATORS = {'(', ')', '{', '}', ',', ';'};

    		    public static void analyze(String text) {
    		        String[] tokens = tokenize(text);  // Step 1: Tokenize the input
    		        boolean isValid = true;  // Track if any errors occur

    		        for (String token : tokens) {
    		            String classification = classifyToken(token);
    		            System.out.println(classification);
    		            if (classification.contains("UNKNOWN") || classification.contains("INVALID")) {
    		                isValid = false;  // Mark as invalid if any error is found
    		            }
    		        }

    		        if (isValid) {
    		            typeCheck(tokens);  // Step 2: Perform type-checking if the input is valid
    		            
    		        }
    		    }

    		    // Tokenize input by adding spaces around separators and operators for easy splitting
    		    private static String[] tokenize(String text) {
    		        for (char separator : SEPARATORS) {
    		            text = text.replace(String.valueOf(separator), " " + separator + " ");
    		        }
    		        for (String operator : OPERATORS) {
    		            text = text.replace(operator, " " + operator + " ");
    		        }
    		        return text.trim().split("\\s+");
    		    }

    		    // Classify tokens and handle edge cases
    		    private static String classifyToken(String token) {
    		        if (isKeyword(token)) return "(" + token + ", KEYWORD)";
    		        if (isOperator(token)) return "(" + token + ", OPERATOR)";
    		        if (isSeparator(token)) return "(" + token + ", SEPARATOR)";
    		        if (token.matches("\\d+(\\.\\d+)?")) return "(" + token + ", NUMBER)";
    		        if (token.matches("\".*\"")) return "(" + token + ", STRING)";
    		        if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) return "(" + token + ", IDENTIFIER)";
    		        if (token.matches("[0-9][a-zA-Z_]+")) return "(" + token + ", INVALID IDENTIFIER)";  // Handle identifiers starting with numbers
    		        if (token.matches("[^\\w\\s]")) return "(" + token + ", UNRECOGNIZED CHARACTER)";  // Handle unrecognized characters
    		        return "(" + token + ", UNKNOWN)";
    		    }

    		    // Check if a token is a keyword
    		    private static boolean isKeyword(String token) {
    		        return Arrays.asList(KEYWORDS).contains(token);
    		    }

    		    // Check if a token is an operator
    		    private static boolean isOperator(String token) {
    		        return Arrays.asList(OPERATORS).contains(token);
    		    }

    		    // Check if a token is a separator (single-character symbols)
    		    private static boolean isSeparator(String token) {
    		        return token.length() == 1 && new String(SEPARATORS).contains(token);
    		    }

    		    // Basic type checking for variable types and assignments
    		    private static void typeCheck(String[] tokens) {
    		        String currentType = null;
    		        String currentVar = null;

    		        for (int i = 0; i < tokens.length - 2; i++) {
    		            if (isDataType(tokens[i]) && tokens[i + 1].matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
    		                currentType = tokens[i];
    		                currentVar = tokens[i + 1];
    		            }

    		            if (tokens[i].matches("[a-zA-Z_][a-zA-Z0-9_]*") && tokens[i + 1].equals("=")) {
    		                String assignedType = getTypeOfValue(tokens[i + 2]);
    		                if (currentVar != null && currentVar.equals(tokens[i]) && !isCompatibleType(currentType, assignedType)) {
    		                    System.out.println("Type mismatch: cannot assign value of type '" +
    		                            assignedType + "' to variable '" +
    		                            tokens[i] + "' of type '" + currentType + "'");
    		                }
    		            }
    		        }
    		    }

    		    // Check if a token is a data type
    		    private static boolean isDataType(String token) {
    		        return token.equals("int") || token.equals("float") || token.equals("String") || token.equals("Boolean");
    		    }

    		    // Determine the type of a value (int, float, String, Boolean, or unknown)
    		    private static String getTypeOfValue(String token) {
    		        if (token.matches("\\d+")) return "int";
    		        if (token.matches("\\d+\\.\\d+")) return "float";
    		        if (token.matches("\".*\"")) return "String";
    		        if (token.equals("true") || token.equals("false")) return "Boolean";
    		        return "unknown";  // Unrecognized value
    		    }

    		    // Check if assigned type is compatible with the variable's type
    		    private static boolean isCompatibleType(String varType, String assignedType) {
    		        return varType.equals(assignedType) || (varType.equals("float") && assignedType.equals("int"));
    		    }

    		   
	   
	}
