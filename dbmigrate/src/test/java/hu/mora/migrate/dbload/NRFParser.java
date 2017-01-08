package hu.mora.migrate.dbload;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;

public class NRFParser {

    @Test
    public void parse() throws Exception {
        ByteBuffer data = ByteBuffer.allocate(1024);
        data.order(ByteOrder.LITTLE_ENDIAN);
        try (ReadableByteChannel in = Channels.newChannel(NRFParser.class.getResourceAsStream("/125_235.NRF"))) {
            //header is 16 byte
            System.out.println("whole file bytes: " + in.read(data));
            data.flip();
            // 4 bytes
            System.out.println("user id 1 " + data.get());
            System.out.println("user id 2 " + data.get());
            System.out.println("user id 3 " + data.get());
            System.out.println("user id 4 " + data.get());

            // 4 bytes - 8 bytes
            System.out.println("datetime 1 " + data.getShort());
            System.out.println("datetime 2 " + data.getShort());

            // 8 bytes - 16 bytes
            System.out.println("reserved 1 " + data.get());
            System.out.println("reserved 2 " + data.get());
            System.out.println("reserved 3 " + data.get());
            System.out.println("reserved 4 " + data.get());
            System.out.println("reserved 5 " + data.get());
            System.out.println("reserved 6 " + data.get());
            System.out.println("reserved 7 " + data.get());
            System.out.println("reserved 8 " + data.get());

            // results are 24 bytes
            int row = 1;
            System.out.println(data.position());
            while (data.hasRemaining()) {
                System.out.println(format(" === row %d === ", row++));
                // 1-4
                System.out.print("Substancz: " + data.getInt());
                // 5
                System.out.print(" JN: " + data.get());
                // 6
                System.out.print(" Modus: " + data.get());
                // 7-8
                System.out.print(" Potenz: " + data.getShort());
                // 9
                System.out.print(" Kanal: " + data.get());
                // 10
                System.out.print(" Ausgesetzt: " + data.get());
                // 11-12
                System.out.print(" Wert: " + data.getShort());
                // 13
                System.out.print(" Wabe: " + data.get());
                // 14 - 17
                System.out.println(" Verstaerkung: " + data.getInt());
                // 18-21
                data.getInt();
                // 22-23
                data.getShort();
                // 24
                data.get();
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
