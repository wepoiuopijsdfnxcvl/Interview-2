package com.bigname.demo03.dao;


import org.apache.ibatis.annotations.Param;

import com.bigname.demo03.core.Member;

public interface MemberDao {
	Member selectMemberByName(@Param("name")String name)throws Exception;
}
