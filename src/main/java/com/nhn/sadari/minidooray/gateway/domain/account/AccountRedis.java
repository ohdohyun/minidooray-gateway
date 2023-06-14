package com.nhn.sadari.minidooray.gateway.domain.account;

import com.nhn.sadari.minidooray.gateway.enumclass.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "AccountRedis", timeToLive = 3600)
public class AccountRedis implements Serializable {
    Long accountId;
    String loginId;
    String username;
    MemberStatusType status;
}
