package com.nowcoder.humanresources.dao;

import com.nowcoder.humanresources.entity.AwardPunishment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AwardPunishmentMapper {
    AwardPunishment selectAwardPunishmentById(@Param("id") String id);
    List<AwardPunishment> selectByName(@Param("name") String name);

    int insertAwardPunishment(AwardPunishment awardPunishment);

    int deleteAwardPunishmentById(@Param("id") String id);

    int updateAwardPunishment(AwardPunishment awardPunishment);

    List<AwardPunishment> getAllAwardPunishments(@Param("offset") int offset, @Param("limit") int limit);

    int countAwardPunishments();
}