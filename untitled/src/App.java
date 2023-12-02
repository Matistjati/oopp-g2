public class App {
    public static void main(String[] args) {
        String test = "asd/asasdAasd/asdasd";
        String[] exploded = test.split("/", 2);
        System.out.println(exploded[0]);
        System.out.println(exploded[1]);
    }
}
