package com.jit.cx.dao;

import com.jit.cx.dao.provider.DocumentDynaSqlProvider;
import com.jit.cx.domain.Document;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

import static com.jit.cx.util.Constants.DOCUMENTTABLE;
public interface DocumentDao {
	//查询
	@Results({@Result(column="id",property="id",id=true),//id=true，表示为主键
			@Result(column="title",property="title"),
			@Result(column="filename",property="filename"),
			@Result(column="remark",property="remark"),
			@Result(column="create_time",property="create_time"),
			@Result(column="user_id",property="user",one=@One(select="com.jit.cx.dao.UserDao.get_Info",fetchType= FetchType.EAGER))})
	@Select("select * from document_inf")
	List<Document> get_List();
	@Select("select * from "+DOCUMENTTABLE+" where title like CONCAT('%',#{content},'%')")
	List<Document> get_LikeList(String content);
	
	
	@SelectProvider(type=DocumentDynaSqlProvider.class,method="insert")
	void insert_Info(Document dept);
	
	@Select("select * from "+DOCUMENTTABLE+" where id = #{id}")
	Document get_Info(Integer id);

	@SelectProvider(type=DocumentDynaSqlProvider.class,method="update")
	void update_Info(Document dept);
	// 根据id删除部门
	@Delete(" delete from "+DOCUMENTTABLE+" where id = #{id} ")
	void delete_Info(Integer id);

	
}
