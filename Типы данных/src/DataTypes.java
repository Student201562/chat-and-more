import java.util.ArrayList;
import java.util.List;

public class DataTypes {
    public static void main(String[] args) {
//  Примитивные типы
        // Вариация объявлений
        boolean active = true;
        int x;
        byte num = 50;
        char c='s';
        double d = 1.5;

        // Система счисления
        int num111 = 0x6F; // 16-тиричная система, число 111
                            // A = 10, B = 11, C = 12, D = 13, E = 14, F = 15
                            // 6F 16 = 6 * 16^1 + 15 * 16^0 = 111
        int num8 = 010; // 8-ричная система, число 8
        int num13 = 0b1101; // 2-ичная система, число 13

        //Использование суффиксов
        float fl = 30.6f;
        double db = 30.6; // в java тип данных по умолчанию,
                        // поэтому значение в типе float без суффикса будет считаться double

        // Символы и строки
        char ch= 103; // символ 'g'
        System.out.println(ch);

        // не примитивный тип (Объектные)
        String hello = "Hellow";
        System.out.println(hello);

        hello.length();

        // Можно пересваивать значения
        int number = 5;
        number = 57;
        number = 89;

        // Константы
        final int constInt = 5;
        // constInt = 345; так сделать нельзя

        // Преоброзование
        int a = 4; // 100
        byte b = (byte) a;
        //System.out.println(b - "fdfg");

        // Потеря данных при преоброзовании
        double a1 = 56.9898;
        int b1 = (int)a1;
        System.out.println(b1);

        int a2 = 3;
        double b2 = 4.6;
        double c2 = a2+b2; // если один из переменных double второй преобразуется к double

        System.out.println(c2);

        // операции сдвига
        System.out.println(a<<1); // 8
        System.out.println(a>>1); // 2


        // Автоупаковка и распаковка это функция преобразования примитивных типов в объектные и наоборот

        //автоупаковка
        List<Integer> listInteger = new ArrayList<Integer>();
        listInteger.add(6);
        listInteger.add(8);
        // Автоупаковка применяется компилятором Java в следующих условиях:
        // Когда значение примитивного типа передается в метод в качестве параметра метода, который ожидает объект соответствующего класса-оболочки.
        // Когда значение примитивного типа присваивается переменной, соответствующего класса оболочки.
         int sum = sumEvenNumbers(listInteger);
        System.out.println(sum);

        // распаковка
        int in = 0;
        in = new Integer(9);
        System.out.println(in);
        // Когда объект передается в качестве параметра методу, который ожидает соответствующий примитивный тип.
        // Когда объект присваивается переменной соответствующего примитивного типа.

        // 2. Распаковка через присвоение
        List<Double> doubleList = new ArrayList<Double>();
        doubleList.add(3.1416);

        // Распаковка через присвоение
        double phi = doubleList.get(0);
        System.out.println("phi = " + phi);


        //Распаковка через вызов метода
        Integer inInt = new Integer(-8);
        int absVal = absoluteValue(inInt);
        System.out.println(absVal);


        // Точность чисел с плавающей точкой
        double a4 = 2.0 - 1.1; // 0,8999999
        System.out.println(a4); // Если вам нужно исключить ошибки округления, следует использовать класс BigDecimal».

    }

    // Пример Автоупаковки
    public static int sumEvenNumbers(List<Integer> intList ) {
        int sum = 0;
            for (Integer i: intList)
                if ( i % 2 == 0 ) // здесь i компилятор преобразовывает из Integer в int к примитивному типу, потому что
                    // % и += не могутприменяться к классу-оболочке.
                    sum += i;
        return sum;
    }

    public static int absoluteValue(int i) {
        return (i < 0) ? -i : i;
    }

}
