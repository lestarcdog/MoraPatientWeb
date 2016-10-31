import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

public class ParseAndLoadCities {

    public static void main(String[] args) throws FileNotFoundException {
        Multimap<String, String> cities = ArrayListMultimap.create();
        try (Scanner scanner = new Scanner(ParseAndLoadCities.class.getResourceAsStream("/telepulesek.txt"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Splitter.on("\t").splitToList(line);
                String zip = row.get(0);
                String city = row.get(1);
                cities.put(city, zip);
            }
        }

        System.out.println(cities.size());
        String tmpl = "INSERT INTO cities(NAME,ZIPS,SMALLCAPS) VALUES('%s','%s','%s');";
        cities.asMap().forEach((city, zips) -> System.out.println(format(tmpl, city, Joiner.on("|").join(zips), city.toLowerCase())));
    }
}
