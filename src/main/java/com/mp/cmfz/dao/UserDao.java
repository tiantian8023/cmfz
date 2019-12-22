package com.mp.cmfz.dao;

import com.mp.cmfz.entity.ProvinceDto;
import com.mp.cmfz.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {

    int findUserByRegisterTime(@Param("start") int start, @Param("end") int end);

    List<ProvinceDto> findUserCountByMap(String sex);
}
