package com.blackbird.dao;

import org.blackbird.dao.PersonDAO;
import org.blackbird.db.MongoUtil;
import org.blackbird.entity.Pessoa;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class PessoaDAOTest {

	private Mongo mongo;
	private Morphia morphia;
	private PersonDAO pessoaDao;
	private final String dbname = "pessoadb";

	@Before
	public void initiate() {
		mongo = MongoUtil.getMongo();
		morphia = new Morphia();
		morphia.map(Pessoa.class);
		pessoaDao = new PersonDAO(mongo, morphia, dbname);
	}

	@After
	public void end() {
		mongo.dropDatabase(dbname);
	}

	@Test
	public void testInsert() {
		long counter = pessoaDao.count();
		Pessoa p = new Pessoa();
		p.setName("maria");
		pessoaDao.save(p);
		long newCounter = pessoaDao.count();
		org.junit.Assert.assertTrue((counter + 1) == newCounter);
	}

	@Test
	public void testFindById() {
		ObjectId oid = (ObjectId) insertPessoa("João");
		Pessoa person = pessoaDao.get(oid);
		org.junit.Assert.assertNotNull(person.getName());
	}

	@Test
	public void testFindList() {
		insertPessoa("Maria");
		insertPessoa("José");
		DBCollection list = pessoaDao.getCollection();
		org.junit.Assert.assertEquals(2, list.getCount());
	}
	
	@Test
	public void testFindByField() {
		insertPessoa("Maria");
		Query<Pessoa> query = pessoaDao.createQuery().field("name").equal("Maria");
		Pessoa person = query.get();
		org.junit.Assert.assertEquals("Maria", person.getName());
	}

	private Object insertPessoa(String string) {
		Pessoa person = new Pessoa();
		person.setName(string);
		return pessoaDao.save(person).getId();
	}

	@Test
	public void testUpdate() {
		ObjectId oid = (ObjectId) insertPessoa("João");
		Pessoa person = pessoaDao.get(oid);
		person.setName("Ronaldo");
		pessoaDao.save(person);
		person = pessoaDao.get(oid);
		Assert.assertEquals("Ronaldo",person.getName());
	}
	
	
	@Test
	public void testDelete() {
		ObjectId oid = (ObjectId) insertPessoa("João");
		pessoaDao.deleteById(oid);
		Assert.assertNull(pessoaDao.get(oid));
	}
}
