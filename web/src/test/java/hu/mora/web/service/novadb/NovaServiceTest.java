package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class NovaServiceTest {

    NovaService service = new NovaService();

    NovaDbConnector connector = new NovaDbConnector();

    @Mock
    ConfigDao dao;

    @Before
    public void setUp() throws Exception {
        when(dao.getValue(any())).thenReturn("C:\\NovaDB");
        connector.configDao = dao;

        service.configDao = dao;
        service.novaDb = connector;

    }
}