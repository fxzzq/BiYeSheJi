package com.xzq.dao;

import java.util.List;

public interface ImportRepository {
  
	public void saveExaminations(String sql,List<Object[]> params);
}
