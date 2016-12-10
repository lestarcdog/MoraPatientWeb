package hu.mora.migrate.sql.elhelements;

import com.google.common.base.Splitter;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreateELHElementsSql {

    private static final String ENG = "/ELG_ENG.csv";
    private static final String HU = "/ELG_UNG.csv";

    private static final String INSERT_TMPL = "INSERT INTO ELHELEMENTS(NAME_HU,NAME_EN) VALUES('%s','%s');\n";

    private static final Splitter SPLITTER = Splitter.on(";");

    public static void main(String[] args) throws FileNotFoundException {
        //read english
        Map<Integer, String> english = readFileToMap(ENG);

        //read hun
        Map<Integer, String> hungarian = readFileToMap(HU);

        //create sql file print to sys out
        PrintWriter writer = new PrintWriter(new FileOutputStream("elh_elements.sql"), true);
        Escaper escaper = Escapers.builder().addEscape('\'', "''").build();
        english.forEach((id, eng) -> {
            String hun = hungarian.getOrDefault(id, "");
            writer.write(String.format(INSERT_TMPL, escaper.escape(hun), escaper.escape(eng)));
        });
        writer.close();

    }

    private static Map<Integer, String> readFileToMap(String filePath) {
        HashMap<Integer, String> map = new HashMap<>();
        try (Scanner scanner = new Scanner(CreateELHElementsSql.class.getResourceAsStream(filePath), "Cp852")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> splitted = SPLITTER.splitToList(line);
                Integer id = Integer.valueOf(splitted.get(1));
                String name = splitted.get(5);
                map.put(id, name);
            }
        }
        return map;
    }
}
