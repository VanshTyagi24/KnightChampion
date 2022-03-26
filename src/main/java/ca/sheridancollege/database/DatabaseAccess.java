package ca.sheridancollege.database;


import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.beans.KnightBean;

@Repository
public class DatabaseAccess {
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	
	public void addKnight() {
       
        String query = "INSERT into knight VALUES(NULL,'test', 0,0,0,58.1)";
        jdbc.update(query, new HashMap());
}
	public void addKnight(KnightBean knight) {
		
		
    	MapSqlParameterSource namedParameters = 
    			new MapSqlParameterSource();
    	
    	
    	
    	String query = "Insert Into knight(knight_name,trials,positionX,positionY, average)VALUES(:name,:trials,:posX,:posY,:average)";
    	namedParameters.addValue("name",knight.getKnightName())
    	.addValue("trials", knight.getTrial())
    	.addValue("posX", knight.getPositionX())
    	.addValue("posY", knight.getPositionY())
    	.addValue("average", knight.getAverage())
    	;

    	
    	int numInsertions = jdbc.update(query, namedParameters);
    	if(numInsertions>0) {
    	
    	System.out.println("The knight was created");
    	}
    }
}
