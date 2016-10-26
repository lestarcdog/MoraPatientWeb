import com.google.common.base.Splitter;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.String.format;

public class ParseAndLoadCities {

    public static void main(String[] args) throws FileNotFoundException {
        Set<String> cities = new HashSet<>();
        try (Scanner scanner = new Scanner(ParseAndLoadCities.class.getResourceAsStream("/telepulesek.txt"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String city = Splitter.on("\t").splitToList(line).get(1);
                cities.add(city);
            }
        }

        System.out.println(cities.size());
        String tmpl = "INSERT INTO cities(NAME,SMALLCAPS) VALUES('%s','%s');";
        cities.forEach(city -> System.out.println(format(tmpl, city, city.toLowerCase())));
    }
}
