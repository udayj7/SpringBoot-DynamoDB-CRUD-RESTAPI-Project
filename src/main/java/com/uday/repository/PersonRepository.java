package com.uday.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.uday.entity.Person;

@Repository
public class PersonRepository {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public Person save(Person person) {
		dynamoDBMapper.save(person);
		return person;
	}

	public Person findById(String id) {
		Person load = dynamoDBMapper.load(Person.class, id);
		return load;
	}

	public PaginatedScanList<Person> findAll() {
		PaginatedScanList<Person> scan = dynamoDBMapper.scan(Person.class, new DynamoDBScanExpression());
		return scan;
	}

	public String update(String id, Person person) {
		dynamoDBMapper.save(person, new DynamoDBSaveExpression().withExpectedEntry("id",
				new ExpectedAttributeValue(new AttributeValue().withS(id))));
		return id;
	}

	public String delete(String id) {
		Person load = dynamoDBMapper.load(Person.class, id);
		dynamoDBMapper.delete(load);
		return "Person deleted successfully:: " + id;
	}

}
