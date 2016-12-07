package hu.mora.tool.commands.wildfly;

import hu.mora.tool.commands.AbstractCommands;
import hu.mora.tool.commands.ProcessExecutor;
import hu.mora.tool.configuration.MoraPaths;
import hu.mora.tool.exception.MoraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WildflyCommands extends AbstractCommands {

    @Autowired
    MoraPaths moraPaths;

    public void startWildfly() throws MoraException {
        try {
            Process exec = ProcessExecutor.exec(moraPaths.wildfly.startBatPath().toString());
            if (!exec.waitFor(30, TimeUnit.SECONDS)) {
                throw new MoraException("A szerver nem indítható el.");
            }
        } catch (Exception e) {
            throw new MoraException("A szerver nem indítható el.");
        }
    }

    public boolean isWildflyRunning() throws MoraException {
        try {
            Process proc = ProcessExecutor.exec(moraPaths.wildfly.jbossCliPath().toString(), "-c", "--command=pwd");
            proc.waitFor();
            return proc.exitValue() == 0;
        } catch (InterruptedException | IOException e) {
            throw new MoraException(e.getMessage(), e);
        }
    }

    public void stopWildfly() throws MoraException {
        try {
            Process exec = ProcessExecutor.exec(moraPaths.wildfly.jbossCliPath().toString(), "-c", "--command=shutdown");
            if (exec.waitFor(30, TimeUnit.SECONDS)) {
                throw new MoraException("A szerver nem állítható le. Valószínű nem fut.");
            }
        } catch (Exception e) {
            throw new MoraException("A szerver nem állítható le. Valószínű nem fut.", e);
        }
    }


}
