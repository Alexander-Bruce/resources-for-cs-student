import java.math.BigInteger;
import java.util.Random;

public class Operation {
    private BigInteger number1;
    private BigInteger number2;
    private int operationType;
    public BigInteger getNumber1() {
        return number1;
    }

    public BigInteger getNumber2() {
        return number2;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setNumber1(int minSize, int maxSize) {
        Random rand = new Random();
        this.number1 = new BigInteger(rand.nextInt(maxSize - minSize + 1) + minSize + "");
    }

    public void setNumber2(int minSize, int maxSize) {
        Random rand = new Random();
        this.number2 = new BigInteger(rand.nextInt(maxSize - minSize + 1) + minSize + "");
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }


    public BigInteger getResult(){
        int type = getOperationType();
        BigInteger num1 = getNumber1();
        BigInteger num2 = getNumber2();
        if(num1 == null || num2 == null) return null;
        if(type == 0) return new BigInteger(num1.add(num2) + "");
        else if(type == 1) return new BigInteger(num1.subtract(num2)+ "");
        else if(type == 2) return new BigInteger(num1.multiply(num2)+ "");
        else return new BigInteger(num1.divide(num2)+ "");
    }
}
