package com.kwon.smb.user;


public interface UserMapper {

	public abstract User getUserById(User m);
	// return타입
		// insert, update, delete : int
		// select : List<자바빈> or 자바빈
	// method명 : id와 맞추기
	// parameter : parameterType과 맞추기

	public abstract int join(User m);
}












