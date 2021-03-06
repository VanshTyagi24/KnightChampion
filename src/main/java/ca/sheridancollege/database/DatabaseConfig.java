package ca.sheridancollege.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DatabaseConfig {
	@Bean
    @Primary
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = 
				new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
		
	}
	 @Bean
	 public DataSource loadSchema() {
	        return new EmbeddedDatabaseBuilder()
	                .setType(EmbeddedDatabaseType.H2)
	                .addScript("classpath:schema.sql")
	                //you can include multiple addScript
	                //for multiple sql files
	                .build();
	        
	    }
	 @Bean
	 public NamedParameterJdbcTemplate
	    namedParameterJdbcTemplate(DataSource datasource) {
	        return new NamedParameterJdbcTemplate(datasource);
	    }
}
