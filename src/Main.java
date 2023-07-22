public class Main {
    public static void main(String[] args) {

        HashMap<String, String> hac = new HashMap<>(28);
        String priviusValue = hac.put("222222", "Коля");
        priviusValue = hac.put("33333", "Юра");
        priviusValue = hac.put("33333", "Дима");

        String getValue = hac.get("222222");
        String removeValue = hac.remove("33333");
        removeValue = hac.remove("33333");
    }
}