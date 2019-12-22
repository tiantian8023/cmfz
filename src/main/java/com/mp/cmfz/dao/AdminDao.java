package com.mp.cmfz.dao;

import com.mp.cmfz.entity.Admin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminDao extends Mapper<Admin> {
}
