package hu.mora.tool.commands.wildfly;

import hu.mora.tool.commands.AbstractCommands;
import hu.mora.tool.commands.ProcessExecutor;
import hu.mora.tool.configuration.Config;
import hu.mora.tool.configuration.MoraPaths;
import hu.mora.tool.exception.MoraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WildflyCommands extends AbstractCommands {

    @Autowired
    private MoraPaths moraPaths;

    @Autowired
    private Config config;

    public void startWildfly() throws MoraException {
        try {
            ProcessExecutor.exec(moraPaths.wildfly.startBatPath().toString());
            //check running in every 5 seconds max retry 10 times 50s max
            //TODO pass it to config
            int maxTry = 10;
            int waited = 0;
            while (!isWildflyRunning()) {
                if (waited++ > maxTry) {
                    throw new MoraException("A szerver lehet hogy nem indult el. Ellenőrizze hogy be tud-e lépni");
                }
                //wait 5s
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            throw new MoraException("A szerver nem indítható el.", e);
        }
    }

    public boolean isWildflyRunning() throws MoraException {
        try {
            Process proc = ProcessExecutor.exec(moraPaths.wildfly.jbossCliPath().toString(), "-c", "--controller=" + config.getWildflyManagementAddress(), "--command=pwd");
            proc.waitFor();
            return proc.exitValue() == 0;
        } catch (InterruptedException | IOException e) {
            throw new MoraException(e.getMessage(), e);
        }
    }

    public void stopWildfly() throws MoraException {
        try {
            Process exec = ProcessExecutor.exec(moraPaths.wildfly.jbossCliPath().toString(), "-c", "--controller=" + config.getWildflyManagementAddress(), "--command=shutdown");
            if (!exec.waitFor(30, TimeUnit.SECONDS)) {
                throw new MoraException("A szerver nem állítható le. Valószínű nem fut.");
            }
        } catch (Exception e) {
            throw new MoraException("A szerver nem állítható le. Valószínű nem fut.", e);
        }
    }


}
