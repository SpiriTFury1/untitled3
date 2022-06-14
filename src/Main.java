import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean x = true;
        while (x) {
            String inputStr; // для чисел арабский и римских
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите выражение. Принимаются числа от 1 до 10 включительно, либо римские от I до X включительно: ");
            inputStr = scanner.nextLine();
            if (calc(inputStr) == -1) // отправляем строку на подсчет
                break;
        }
        System.out.println("Программа завершила работу");
    }

    private static Integer calc(String string) { // string = "10 + 1" "IV + I"
        Integer result = -1; // для ошибки
        String firstNumber, secondNumber, operation;
        String[] str = string.split(" "); // так введенная строка попадет в массив
        firstNumber = str[0]; // в этой переменной будет лежать "III" или 3
        operation = str[1]; // в этой переменной будет лежать лог операция
        secondNumber = str[2]; // в этой переменной будет лежать "IV" или 4

        Data firstData, secondData;
        firstData = parseData(firstNumber); // пробуем спарсить первое число
        if (firstData.getCode().equals(Data.CODE_ERROR)) { // если не найдено число
            System.out.println("Ошибка не найдено число");
            return result; // возвращаем результат -1
        } else {
            if (!checkNumber(firstData.getResult())) { // если число не прошло проверку в диапозоне
                System.out.println("Ошибка введите число от 1 до 10 включительно");
                return result; // возвращаем результат -1
            }
        }
        secondData = parseData(secondNumber); // пробуем спарсить второе число
        if (secondData.getCode().equals(Data.CODE_ERROR)) { // если не найдено число
            System.out.println("Ошибка не найдено число");
            return result; // возвращаем результат -1
        } else {
            if (!checkNumber(secondData.getResult())) { // если число не прошло проверку в диапозоне
                System.out.println("Ошибка введите число от 1 до 10 включительно");
                return result; // возвращаем результат -1
            }
        }

        boolean allNumbersArab = (firstData.getCode().equals(Data.CODE_ARAB) && secondData.getCode().equals(Data.CODE_ARAB));
        boolean allNumbersRim = (firstData.getCode().equals(Data.CODE_RIM) && secondData.getCode().equals(Data.CODE_RIM));

        if (allNumbersArab || allNumbersRim) {
            if (Objects.equals(operation, "+")) {
                result = firstData.getResult() + secondData.getResult();
            } else if (Objects.equals(operation, "-")) {
                result = firstData.getResult() - secondData.getResult();
                if (allNumbersRim) { // если оба числа римских
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
        if (allNumbersRim)
            System.out.println("Результат вычисления = " + arabToRim(result));
        else
            System.out.println("Результат вычисления = " + result);
        return result;
    }

    private static boolean checkNumber(Integer number) { // проверка на условие от 1 до 10 включительно
        return (number > 0 && number <= 10);
    }

    private static Data parseData(String inputStr) {
        Data result = new Data();
        result.setCode(Data.CODE_ERROR);
        Integer number;
        try {
            number = Integer.parseInt(inputStr); // пробуем получить из строки арабское число
            result.setResult(number); // присваиваем результат число
            result.setCode(Data.CODE_ARAB); // присваиваем код - арабское число
        } catch (NumberFormatException e) { // если получено исключение при парсинге строки
            number = parseRimNumber(inputStr); // пробуем получить арабское число из римских символов
            if (number != -1) { // если нашли римское число
                result.setResult(number); // присваеваем результат найденное число
                result.setCode(Data.CODE_RIM); // присваиваем код - арабское число
            }
        }
        return result; // если удалось получить арабское число оно будет возвращено
    }

    private static String arabToRim(Integer i) {
        String[] rimNumbers = {
                "0", " I ", " II ", " III ", " IV ", " V ", " VI ", " VII ", " VIII ", " IX ", " X ", " XI ", " XII ", " XIII ", " XIV ", " XV ", " XVI ", " XVII ", " XVIII ", " XIX ", " XX ", " XXI ", " XXII ", " XXIII ", " XXIV ", " XXV ", " XXVI ", " XXVII ", " XXVIII ", " XXIX ", " XXX ", " XXXI ", " XXXII ", " XXXIII ", " XXXIV ", " XXXV ", " XXXVI ", " XXXVII ", " XXXVIII ", " XXXIX ", " XL ", " XLI ", " XLII ", " XLIII ", " XLIV ", " XLV ", " XLVI ", " XLVII ", " XLVIII ", " XLIX ", " L ", " LII ", " LIII ", " LIV ", " LV ", " LVI ", " LVII ", " LVIII ", " LIX ", " LX ", " LXI ", " LXII ", " LXIII ", " LXIV ", " LXV ", " LXVI ", " LXVII ", " LXVIII ", " LXIX ", " LXX ", " LXXI ", " LXXII ", " LXXIII ", " LXXIV ", " LXXV ", " LXXVI ", " LXXVII ", " LXXVIII ", " LXXIX ", " LXXX ", " LXXXI ", " LXXXII ", " LXXXIII ", " LXXXIV ", " LXXXV ", " LXXXVI ", " LXXXVII ", " LXXXVIII ", " LXXXIX ", " XC ", " XCI ", " XCII ", " XCIII ", " XCIV ", " XCV ", " XCVI ", " XCVII ", " XCVIII ", " XCIX ", " C "};
        return rimNumbers[i];
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
/*
String[] rimNumbers = {
" I " , , " III " , " IV " , " V " , " VI " , " VII " , " VIII " , " IX " , " X " , " XI " , " XII " , " XIII " , " XIV " , " XV " , " XVI " , " XVII " , " XVIII " , " XIX " , " XX " , " XXI " , " XXII " , " XXIII " , " XXIV " , " XXV " , " XXVI " , " XXVII " , " XXVIII " , " XXIX " , " XXX " , " XXXI " , " XXXII " , " XXXIII " , " XXXIV " , " XXXV " , " XXXVI " , " XXXVII " , " XXXVIII " , " XXXIX " , " XL " , " XLI " , " XLII " , " XLIII " , " XLIV " , " XLV " , " XLVI " , " XLVII " , " XLVIII " , " XLIX " , " L " , " LII " , " LIII " , " LIV " , " LV " , " LVI " , " LVII " , " LVIII " , " LIX " , " LX " , " LXI " , " LXII " , LXIII " , " LXIV " , " LXV " , " LXVI " , " LXVII " , " LXVIII " , " LXIX " " LXX " , " LXXI " , " LXXII " , " LXXIII " , " LXXIV " , " LXXV " , " LXXVI " , " LXXVII " , " LXXVIII " , " LXXIX " , " LXXX “ , " LXXXI " , " LXXXII " , " LXXXIII " , " LXXXIV " , " LXXXV " , " LXXXVI " , " LXXXVII " , " LXXXVIII " , " LXXXIX " , " XC " XCI " , " XCII " , " XCIII " , " XCIV " , " XCV " , " XCVI " , " XCVII " , " XCVIII " , " XCIX " , " C " } ;
 };*/

