package ExampleGeneric;

public class Test {
    public static void main(String[] args) {
        BoxPrinter<Integer> value1 = new BoxPrinter<Integer>(new Integer(10));
        System.out.println(value1);
        Integer intValue1 = value1.getValue();
        BoxPrinter<String> value2 = new BoxPrinter<String>("Hello world");
        System.out.println(value2);

        // Здесь повторяется ошибка предыдущего фрагмента кода
        // ....Integer intValue2 = value2.getValue();
        // Маленький вывод одного из применения джинериков. На много лучше иметь ошибку при компиляции,
        // чем иметь ее при работе программы

    }


    private static void diamondSyntax(){
        // Для ленивых придумали Алмазный синтаксис
        //BoxGeneric<Integer, String> pair = new Pair<>(6, " Apr");
    }
}
