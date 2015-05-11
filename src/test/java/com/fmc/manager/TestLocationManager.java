package com.fmc.manager;

import com.fmc.edu.manager.LocationManager;
import com.fmc.edu.util.pagenation.Pagination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by dylanyu on 5/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestLocationManager {
    @Resource(name = "locationManager")
    private LocationManager locationManager;

    @Test
    public void testQueryCityPage() {
        Pagination pagination = new Pagination(1, 20);
        Map<String, Object> result = locationManager.queryCityPage(pagination, 1, null);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("*********************************************************************************");
        Map<String, Object> result1 = locationManager.queryCityPage(pagination, 1, null);
    }
}
