package org.blackbird.dao;
import org.blackbird.entity.Pessoa;
import org.bson.types.ObjectId;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

public class PersonDAO extends BasicDAO<Pessoa, ObjectId> {

	public PersonDAO(Mongo mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}
	
}
