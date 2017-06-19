package kr.ac.cnu.configuration;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by rokim on 2017. 6. 9..
 */
@Configuration
@MapperScan(basePackages = "kr.ac.cnu.repository",  sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration {
    @Bean(name = "sqlSessionFactory")
    @ConfigurationProperties(prefix = "application.mybatis")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean(name = "sqlSession", destroyMethod = "clearCache")
    public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
