package com.xu.demo.model;

import java.math.BigDecimal;

/**
 * t_user表
 *
 * @author xushaohua on 2017-11-22.
 */
public class User {
	private Integer id;
	private String name;
	//private String password;
	private BigDecimal money;
	private String age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//public String getPassword() {
	//	return password;
	//}
    //
	//public void setPassword(String password) {
	//	this.password = password;
	//}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
