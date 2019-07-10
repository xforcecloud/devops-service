package local;

import io.choerodon.devops.infra.mapper.DevopsEnvQuotaMapper;
import io.choerodon.mybatis.MybatisMapperAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.cloud.sleuth.log.SleuthLogAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;


/**
 * template for db-test
 */
@RunWith(SpringRunner.class)
@TestConfiguration
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@Import({MybatisMapperAutoConfiguration.class, DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, TraceAutoConfiguration.class, SleuthLogAutoConfiguration.class})
public class TestRepository {


    @Autowired
    DevopsEnvQuotaMapper mapper;

    @Test
    public void testEmpty(){
        mapper.selectByEnvIds(Collections.emptyList()).stream().forEach(System.out::println);
    }

    @Test
    public void testForSomeId(){
        mapper.selectByEnvIds(Arrays.asList(121l)).stream().forEach(System.out::println);
    }
}
