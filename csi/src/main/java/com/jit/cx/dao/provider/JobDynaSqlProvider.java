package com.jit.cx.dao.provider;

import static com.jit.cx.util.Constants.JOBTABLE;

import org.apache.ibatis.jdbc.SQL;

import com.jit.cx.domain.Job;

public class JobDynaSqlProvider {
	// 动态插入
			public String insertDept(Job job){
				
				return new SQL(){
					{
						INSERT_INTO(JOBTABLE);
						if(job.getName() != null && !job.getName().equals("")){
							VALUES("name", "#{name}");
						}
						if(job.getRemark() != null && !job.getRemark().equals("")){
							VALUES("remark", "#{remark}");
						}
					}
				}.toString();
			}	
			// 动态更新
			public String updateDept(Job job){
				
				return new SQL(){
					{
						UPDATE(JOBTABLE);
						if(job.getName() != null){
							SET(" name = #{name} ");
						}
						if(job.getRemark() != null){
							SET(" remark = #{remark} ");
						}
						WHERE(" id = #{id} ");
					}
				}.toString();
			}
}
