package org.example.calculate;

import java.util.Arrays;

public enum ArithmeticOperator {
    ADDITION("+") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 + operand2;
        }
    }, SUBTRACTION("-") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 - operand2;
        }
    }, MULTIPLICATION("*") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 * operand2;
        }
    }, DIVISION("/") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            if(operand2 == 0){
                throw new IllegalArgumentException("0으로는 나눌수 없습니다.");
            }
            return operand1 / operand2;
        }
    };
    private final String operator;

    ArithmeticOperator(String operator) {
        this.operator = operator;
    }

    // 외부에 노출되는 public 인터페이스
    public static int calculate(int operand1, String operator, int operand2) {
        // 해당 enum 값 가져오기
        ArithmeticOperator arithmeticOperator = Arrays.stream(values())
                .filter(v -> v.operator.equals(operator))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));

        return arithmeticOperator.arithmeticCalculate(operand1,operand2);
    }

    // 내부에서 사용
    public abstract int arithmeticCalculate(final int operand1, final int operand2);
}
