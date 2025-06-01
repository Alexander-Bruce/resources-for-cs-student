package fss.exception;

public class SemanticErrorException extends ScriptException {
    public SemanticErrorException(String message, int line) {
        super("Semantic Error on line " + line + ": " + message);
    }
    public SemanticErrorException(String message) {
        super("Semantic Error: " + message);
    }
}