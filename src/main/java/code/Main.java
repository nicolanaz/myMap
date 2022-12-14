package code;

public class Main {
    public static void main(String[] args) {
        MyMap<Integer, String> map = new MyMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");

        System.out.println(map.containsKey(1));
        System.out.println(map.containsValue("two"));
        System.out.println(map.getValue(3));
        System.out.println(map.getKey("one"));
        System.out.println();

        for (MyMap<Integer, String>.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
