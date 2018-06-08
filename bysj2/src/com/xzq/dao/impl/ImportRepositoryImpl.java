package com.xzq.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.xzq.dao.ImportRepository;

@Repository
public class ImportRepositoryImpl implements ImportRepository {
	@Autowired
    private SessionFactory sessionFactory;
	
    private Session getCurrentSession() {
    	//spring来管理事务 getCurrentSession
    	//hibernate自己管理使用sessionFactory.openSession()
        return this.sessionFactory.getCurrentSession();
    }

	@Override
	public void saveExaminations(String sql,List<Object[]> params) {
		
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		//`creator`,`type`,`stem`,`content`,`analysis`,`answer`,`multi`)
		//account,type,stem,JSON.toJSONString(contentList),ani,answer,multi};
		System.out.println("-------params-----"+params);
		//遍历List<Object[]>
		for (Object[] o :params) {
			for (int i = 0; i < o.length; i++) {
				//前6个是String
				if(i<6) {
					query.setString(i,(String) o[i]);
					System.out.println((String) o[i]+"+++++++++++++++++++++"+i);
				}else {
					if(i==(o.length-1)) {
						query.setDouble(i,(double)o[i]);
						System.out.println((double) o[i]+"+++++++++++++++++++++"+i);
					}
					else {
						query.setInteger(i,(int)o[i]);
						System.out.println((int)o[i]+"+++++++++++++++++++++"+i);
					}
					
				}
				
			}
			query.executeUpdate();
	}
		
	}
	

}
