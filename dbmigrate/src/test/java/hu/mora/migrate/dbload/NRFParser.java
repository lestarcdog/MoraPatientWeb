package hu.mora.migrate.dbload;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;

public class NRFParser {

    @Test
    public void parse() throws Exception {
        ByteBuffer data = ByteBuffer.allocate(1024);
        //data.order(ByteOrder.LITTLE_ENDIAN);

        try (ReadableByteChannel in = Channels.newChannel(NRFParser.class.getResourceAsStream("/83_134.NRF"))) {
            System.out.println("whole file bytes: " + in.read(data));
            data.flip();
            System.out.println("user id 1 " + data.get());
            System.out.println("user id 2 " + data.get());
            System.out.println("user id 3 " + data.get());
            System.out.println("user id 4 " + data.get());

            System.out.println("datetime 1 " + data.getShort());
            System.out.println("datetime 2 " + data.getShort());

            //reserved
            System.out.println("reserved 1 " + data.get());
            System.out.println("reserved 2 " + data.get());
            System.out.println("reserved 3 " + data.get());
            System.out.println("reserved 4 " + data.get());
            System.out.println("reserved 5 " + data.get());
            System.out.println("reserved 6 " + data.get());
            System.out.println("reserved 7 " + data.get());
            System.out.println("reserved 8 " + data.get());
            System.out.println("reserved 9 " + data.get());

            int row = 1;
            System.out.println(data.position());
            while (data.hasRemaining()) {
                System.out.println(format(" === row %d === ", row++));
                System.out.print("Substancz: " + data.getInt());
                System.out.print(" JN: " + data.get());
                System.out.print(" Modus: " + data.get());
                System.out.print(" Potenz: " + data.getShort());
                System.out.print(" Kanal: " + data.get());
                System.out.print(" Ausgesetzt: " + data.get());
                System.out.print(" Wert: " + data.getShort());
                System.out.print(" Wabe: " + data.get());
                System.out.println(" Verstaerkung: " + data.getInt());
            }
        }
    }

    @Test
    public void name() throws Exception {
        LocalDateTime dt = LocalDateTime.of(2016, 10, 17, 15, 42, 34);
        System.out.println(dt.toEpochSecond(ZoneOffset.UTC));
        System.out.println(dt.toInstant(ZoneOffset.UTC).toEpochMilli());

    }
}
