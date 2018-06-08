package com.xzq.entity;

public class Classlist {
  private Integer id;
  private String name;
  public void setId(Integer id) {
	this.id = id;
}
  public Integer getId() {
	return id;
}
  public void setName(String name) {
	this.name = name;
}
  public String getName() {
	return name;
}
  
public Classlist() {

}

public Classlist(Integer id, String name) {

	this.id = id;
	this.name = name;
}
@Override
public String toString() {
	return "Classlist [id=" + id + ", name=" + name + "]";
}
  
  
}
