package com.works.sevendays.repositories;

import com.works.sevendays.entitiies.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    long countByName(String name);
}
