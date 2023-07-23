public class Main {
    public static void main(String[] args) {

        HashMap<String, String> hac = new HashMap<>(28);
        String priviusValue = hac.put("222222", "Коля");
        priviusValue = hac.put("33333", "Юра");
        priviusValue = hac.put("33333", "Дима");
        hac.put("232323", "Юля");
        hac.put("545454", "Ира");
        hac.put("665655", "Анна");
        hac.put("676767", "Ира");

//        String getValue = hac.get("222222");
//        System.out.println(getValue);
        StringBuilder res = hac.getAllValue();
        System.out.println(res);

    }
}