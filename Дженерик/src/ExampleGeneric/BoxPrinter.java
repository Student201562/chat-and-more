package ExampleGeneric;

class BoxPrinter<T> { // T - это тип, который должен быть определен позже, конкретнее при создании класса
    // можно создать такой Дженерик BoxPrinter<T1, T2> это уже похоже на словарь, значение ключ
    private T val;

    public BoxPrinter(T arg) {
        val = arg;
    }

    public String toString() {
        return "{" + val + "}";
    }

    public T getValue() {
        return val;
    }
}