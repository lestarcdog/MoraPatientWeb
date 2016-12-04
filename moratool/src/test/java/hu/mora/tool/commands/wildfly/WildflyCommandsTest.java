package hu.mora.tool.commands.wildfly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context.xml")
public class WildflyCommandsTest {


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