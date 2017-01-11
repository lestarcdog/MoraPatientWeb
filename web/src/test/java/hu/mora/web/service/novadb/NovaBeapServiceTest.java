package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NovaBeapServiceTest {

    private NovaBeapService underTest = new NovaBeapService();

    @Mock
    ConfigDao dao;

    @Before
    public void setUp() throws Exception {
        when(dao.getValue(any())).thenReturn("C:\\NovaDB");
        underTest.configDao = dao;

        underTest.setUp();
    }

    @Test
    public void test() throws Exception {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 8, 11, 12);
        System.out.println(Collections.binarySearch(l, 15));
    }
}