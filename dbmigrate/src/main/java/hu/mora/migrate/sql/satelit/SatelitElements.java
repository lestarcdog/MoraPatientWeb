package hu.mora.migrate.sql.satelit;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class SatelitElements {

    private static final String ELEMENTS_SQL = "INSERT INTO SATELIT_ELHELEMENT(ID,NAME_ENG) VALUES(%d,'%s');";
    private static final String ADD_PARENT_SQL = "UPDATE SATELIT_ELHELEMENT SET PARENT_ID = %d WHERE id = %d;";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Map<Integer, Index> indices = new HashMap<>();
        Map<Integer, Struc> strucs = new HashMap<>();

        URI indexUri = SatelitElements.class.getResource("/INDEX.ENU").toURI();
        Files.readAllLines(Paths.get(indexUri)).forEach(line -> {
            String[] split = line.split("/");
            Index i = new Index();
            i.id = Integer.valueOf(split[1]);
            i.name = split[0];
            indices.put(i.id, i);
        });

        URI strucUri = SatelitElements.class.getResource("/STRUC.ENU").toURI();
        Files.readAllLines(Paths.get(strucUri)).forEach(line -> {
            String[] split = line.split("/");
            Struc s = new Struc();
            s.id = Integer.valueOf(split[1]);
            s.parentId = Integer.valueOf(split[3]);
            strucs.put(s.id, s);
        });

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("insert_satelit_elements.sql"),
                Charset.defaultCharset(),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            bufferedWriter.write("-- Writing elements only");
            bufferedWriter.newLine();
            for (Index index : indices.values()) {
                bufferedWriter.write(String.format(ELEMENTS_SQL, index.id, index.name.replace("'", "''")));
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();
            bufferedWriter.write("-- Write parent-child relationship");
            bufferedWriter.newLine();
            for (Struc struc : strucs.values()) {
                // do not write the all parent it has a circular dependency
                if (struc.id != 0) {
                    bufferedWriter.write(String.format(ADD_PARENT_SQL, struc.parentId, struc.id));
                    bufferedWriter.newLine();
                }
            }
        }

    }

    private static class Index {
        private String name;
        private Integer id;
    }

    private static class Struc {
        private Integer id;
        private Integer parentId;
    }
}
