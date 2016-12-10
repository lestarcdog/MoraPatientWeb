package hu.mora.tool.commands.wildfly;

import hu.mora.tool.commands.SpringBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class WildflyCommandsTest extends SpringBase {


    @Autowired
    WildflyCommands underTest;

    @Test
    public void start() throws Exception {
        underTest.startWildfly();
    }

    @Test
    public void stop() throws Exception {
        underTest.stopWildfly();
    }

    @Test
    public void running() throws Exception {
        System.out.println(underTest.isWildflyRunning());

    }
}