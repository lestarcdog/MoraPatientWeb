import com.google.common.base.Splitter;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.lang.String.format;

public class ParseAndLoadCities {

    public static void main(String[] args) throws FileNotFoundException {
        Set<CityZip> cities = new HashSet<>();
        try (Scanner scanner = new Scanner(ParseAndLoadCities.class.getResourceAsStream("/telepulesek.txt"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Splitter.on("\t").splitToList(line);
                String zip = row.get(0);
                String city = row.get(1);
                cities.add(new CityZip(zip, city));
            }
        }

        System.out.println(cities.size());
        String tmpl = "INSERT INTO cities(NAME,ZIP,SMALLCAPS) VALUES('%s','%s','%s');";
        cities.forEach(cz -> System.out.println(format(tmpl, cz.city, cz.zip, cz.city.toLowerCase())));
    }


    private static class CityZip {
        private final String zip;
        private final String city;

        public CityZip(String zip, String city) {
            this.zip = zip;
            this.city = city;
        }

        public String getZip() {

            return zip;
        }

        public String getCity() {
            return city;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CityZip cityZip = (CityZip) o;

            return city != null ? city.equals(cityZip.city) : cityZip.city == null;

        }

        @Override
        public int hashCode() {
            return city != null ? city.hashCode() : 0;
        }
    }
}
