package com.jit.cx.dao;

import com.jit.cx.dao.provider.NoticeDynaSqlProvider;
import com.jit.cx.domain.Notice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

import static com.jit.cx.util.Constants.NOTICETABLE;

public interface NoticeDao {
	//查询
	@Results({@Result(column="id",property="id",id=true),//id=true，表示为主键
			@Result(column="title",property="title"),
			@Result(column="content",property="content"),
			@Result(column="create_time",property="Create_time"),
			@Result(column="user_id",property="user",one=@One(select="com.jit.cx.dao.UserDao.get_Info",fetchType= FetchType.EAGER))})
	@Select("select * from "+NOTICETABLE+" ")
	List<Notice> get_List();
	@Select("select * from "+NOTICETABLE+"  where title like CONCAT('%',#{content},'%')")
	List<Notice> get_LikeList(String content);

	@SelectProvider(type=NoticeDynaSqlProvider.class,method="insert_Notice")
	void insert_Info(Notice employee);
	
	@Select("select * from "+NOTICETABLE+" where id = #{id}")
	Notice get_Info(Integer id);

	@SelectProvider(type=NoticeDynaSqlProvider.class,method="update_Notice")
	void update_Info(Notice employee);
	// 根据id删除部门
	@Delete(" delete from "+NOTICETABLE+" where id = #{id} ")
	void delete_Info(Integer id);

}
