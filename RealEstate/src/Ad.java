import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Ad {
    int Area;
    Category Category;
    // DateTime CreateAt;
    String CreateAt;
    String Description;
    int Floors;
    Boolean FreeOfCharge;
    int id;
    String ImageUrl;
    String LatLong;
    int Rooms;
    Seller Seller;

    static ArrayList<Ad> adList = new ArrayList<Ad>();

    public Ad() {

    }

    public Ad(int id, int area, Category category, String createAt, String description, int floors,
            Boolean freeOfCharge, String imageUrl, String latLong, int rooms, Seller seller) {
        this.id = id;
        this.Area = area;
        this.Category = category;
        this.CreateAt = createAt;
        this.Description = description;
        this.Floors = floors;
        this.FreeOfCharge = freeOfCharge;
        this.ImageUrl = imageUrl;
        this.LatLong = latLong;
        this.Rooms = rooms;
        this.Seller = seller;

    }

    public static ArrayList<Ad> LoadFromCsv(File file) throws Exception {
        try (Scanner sc = new Scanner(file)) {
            int count = 0;
            int areaSum = 0;
            String line = sc.nextLine();
            while (sc.hasNextLine()) {
                Ad ad = new Ad();
                line = sc.nextLine();
                String[] values = line.split(";");
                ad.id = Integer.parseInt(values[0]);
                ad.Area = Integer.parseInt(values[4]);
                ad.Category = new Category(Integer.parseInt(values[12]), values[13]);
                ad.CreateAt = values[8];
                ad.Description = values[5];
                ad.Floors = Integer.parseInt(values[3]);
                ad.FreeOfCharge = values[6].equals("1");
                ad.ImageUrl = values[7];
                ad.LatLong = values[2];
                ad.Rooms = Integer.parseInt(values[1]);
                ad.Seller = new Seller(Integer.parseInt(values[9]), values[10], values[11]);
                adList.add(ad);

                if (ad.Floors == 0) {
                    areaSum += ad.Area;
                    count++;
                }

            }
            double avg = (double) areaSum / count;
            System.out.print("\033[H\033[2J");
            System.out.printf("1. A földszinti ingatlanok átlagos alapterülete: %.2f m2", avg);
        }
        return adList;
    }

    public static Ad DistanceTo(double xCord, double yCord) {
        Ad nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Ad ad : adList) {
            String[] cords = ad.LatLong.split(",");
            double x = Double.parseDouble(cords[0]);
            double y = Double.parseDouble(cords[1]);

            double distance = Math.sqrt(Math.pow(Math.abs(xCord - x), 2) + Math.pow(Math.abs(yCord - y), 2));

            if (distance < minDistance && ad.FreeOfCharge == true) {
                minDistance = distance;
                nearest = ad;
            }
        }
        System.out.println();
        System.out.println("2. A zenevár óvodához légvonalban a legközelebbi tehermentes ingatlan adatai:");
        System.out.println("\tEladó neve     : " + nearest.Seller.Name);
        System.out.println("\tEladó telefonja: " + nearest.Seller.Phone);
        System.out.println("\tAlapterület    : " + nearest.Area);
        System.out.println("\tSzobák száma   : " + nearest.Rooms);
        System.out.println();
        return nearest;
    }

}
