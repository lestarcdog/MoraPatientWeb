package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.model.novadb.nrf.NrfResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NovaDbConnectorTest {

    @Mock
    ConfigDao dao;

    NovaDbConnector connector = new NovaDbConnector();

    @Before
    public void setUp() throws Exception {
        when(dao.getValue(any())).thenReturn("C:\\NovaDB");
        connector.configDao = dao;
        connector.setUp();

    }

    @Test
    public void parseFile() throws Exception {

        NrfResult result = connector.readUserResult(125, 234);
        System.out.println(result);
    }
}