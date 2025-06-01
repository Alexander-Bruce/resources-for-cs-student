package fss.exception;

public class SyntaxErrorException extends ScriptException {
    public SyntaxErrorException(String message, int line) {
        super("Syntax Error on line " + line + ": " + message);
    }
    public SyntaxErrorException(String message) {
        super("Syntax Error: " + message);
    }
}