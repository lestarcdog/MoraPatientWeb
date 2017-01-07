package hu.mora.tool.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ProcessExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessExecutor.class);

    @SafeVarargs
    public static Process exec(String... cmd) throws IOException {
        LOG.info("Executing command {} ", Arrays.asList(cmd));
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        //TODO environment should be parametrized
        // this is only for wildfly not to stop after execution
        pb.environment().put("NOPAUSE", "cafebabe");
        //pipe it to out.txt because no easy /dev/null
        pb.redirectOutput(new File("out.txt"));
        return pb.start();
    }
}
