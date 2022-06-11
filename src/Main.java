import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            String inputStr; // для чисел арабский и римских
            Integer result = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите выражение. Принимаются числа от 1 до 10 включительно, либо римские от I до X включительно: ");
            inputStr = scanner.nextLine();
            result = calc(inputStr); // отправляем строку на проверку
            if (result != -1)
                System.out.println("Результат вычисления = " + result);
            else
                break;
        }
        System.out.println("Программа завершила работу");
    }

    private static Integer calc(String string) { // string = "цуйцу 10 + 1 цйуцйуйцу IV + I" "gfd + sd"
        Integer result = -1;
        String firstNumber, secondNumber, operation;
        String[] str = string.split(" "); // так введенная строка попадет в массив
        firstNumber = str[0]; // в этой переменной будет лежать "III" или 3
        operation = str[1]; // в этой переменной будет лежать лог операция
        secondNumber = str[2]; // в этой переменной будет лежать "IV" или 4

        Data firstData  = new Data(), secondData = new Data();
        firstData = parseArab(firstNumber); // пробуем спарсить первое число
        if (firstData.getCode().equals(Data.CODE_ERROR)) { // если не найдено число
            System.out.println("Ошибка не найдено число");
            return result; // возвращаем результат -1
        } else {
            if (!checkNumber(firstData.getResult())) { // если число не прошло проверку в диапозоне
                System.out.println("Ошибка введите число от 1 до 10 включительно");
                return result; // возвращаем результат -1
            }
        }
        secondData  = parseArab(secondNumber); // пробуем спарсить второе число
        if (secondData.getCode().equals(Data.CODE_ERROR)) { // если не найдено число
            System.out.println("Ошибка не найдено число");
            return result; // возвращаем результат -1
        } else {
            if (!checkNumber(secondData.getResult())) { // если число не прошло проверку в диапозоне
                System.out.println("Ошибка введите число от 1 до 10 включительно");
                return result; // возвращаем результат -1
            }
        }

        if ((firstData.getCode().equals(Data.CODE_ARAB) && secondData.getCode().equals(Data.CODE_ARAB)) ||
                (firstData.getCode().equals(Data.CODE_RIM) && secondData.getCode().equals(Data.CODE_RIM))) {
            if (Objects.equals(operation, "+")) {
                result = firstData.getResult() + secondData.getResult();
            } else if (Objects.equals(operation, "-")) {
                result = firstData.getResult() - secondData.getResult();
                if (firstData.getCode().equals(Data.CODE_RIM) && secondData.getCode().equals(Data.CODE_RIM)) {
                    if (result < 1) {
                        System.out.println("Ошибка. Были введены римские числа и результат вычислений меньше 1");
                        result = -1;
                    }
                }
            } else if (Objects.equals(operation, "/")) {
                result = firstData.getResult() / secondData.getResult();
            } else if (Objects.equals(operation, "*")) {
                result = firstData.getResult() * secondData.getResult();
            }
        } else {
            System.out.println("Ошибка были введены числа арабское и римское");
        }
        return result;
    }

    private static boolean checkNumber(Integer number) { // проверка на условие от 1 до 10 включительно
        return (number > 0 && number <= 10);
    }

    private static Data parseArab(String inputStr) {
        Data result = new Data();
        result.setCode(Data.CODE_ERROR);
        Integer number;
        try {
            number = Integer.parseInt(inputStr); // пробуем получить из строки арабское число
           // if (number > 0 && number <= 10) { // если подходит по условию от 0 до 10 включительно
            result.setResult(number); // присваиваем результат число
            result.setCode(Data.CODE_ARAB); // присваиваем код - арабское число
           // }
        } catch (NumberFormatException e) { // если получено исключение при парсинге строки
            number = parseRimNumber(inputStr); // пробуем получить арабское число из римских символов
            if (number != -1) { // если нашли римское число
                result.setResult(number); // присваеваем результат найденное число
                result.setCode(Data.CODE_RIM); // присваиваем код - арабское число
            }
        }
        return result; // если удалось получить арабское число оно будет возвращено
    }

    private static Integer parseRimNumber(String a) { // метод для разбора строки с возможными римскими числами
        Integer result = -1;
        switch (a) {
            case "I": {
                result = 1;
                break;
            }
            case "II": {
                result = 2;
                break;
            }
            case "III": {
                result = 3;
                break;
            }
            case "IV": {
                result = 4;
                break;
            }
            case "V": {
                result = 5;
                break;
            }
            case "VI": {
                result = 6;
                break;
            }
            case "VII": {
                result = 7;
                break;
            }
            case "VIII": {
                result = 8;
                break;
            }
            case "IX": {
                result = 9;
                break;
            }
            case "X": {
                result = 10;
                break;
            }
        }
        return result;
    }
}