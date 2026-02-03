package com.Project.SpringBootDemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // change normal class into entity class
@Table(name="users")//to keep costom name for table
public class userEntity {
	@Id // to make id as primary key
	@GeneratedValue(strategy=GenerationType.AUTO)// auto-increment
private Long id;
private String name;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
private String email;
public userEntity(long id, String name, String email) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
}
public userEntity() {
	super();
}

}
