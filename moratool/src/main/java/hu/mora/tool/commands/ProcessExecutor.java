package hu.mora.tool.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ProcessExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessExecutor.class);

    @SafeVarargs
    public static Process exec(String... cmd) throws IOException {
        LOG.info("Executing command {} ", (Object[]) cmd);
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        pb.environment().put("NOPAUSE", "cafebabe");
        pb.redirectOutput(new File("out.txt"));
        return pb.start();
    }
}
