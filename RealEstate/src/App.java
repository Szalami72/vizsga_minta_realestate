import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {

        File file = new File("realestates.csv");
        Ad.LoadFromCsv(file);
        Ad.DistanceTo(47.4164220114023, 19.066342425796986);

    }
}
