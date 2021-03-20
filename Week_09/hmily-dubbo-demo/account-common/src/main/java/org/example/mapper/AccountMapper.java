package org.example.mapper;


import org.example.dto.AccountDTO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

    @Update("update account set account_balance = account_balance - #{sourceMoney}," +
            "account_balance_locked = account_balance_locked + #{sourceMoney}" +
            " where user_id = #{userId} and account_type = #{sourceAccountType} and account_balance >= #{sourceMoney}")
    int freezeMoney(AccountDTO accountDTO);

    @Update("update account set account_balance_locked = account_balance_locked - #{sourceMoney}" +
            " where user_id = #{userId} and account_type = #{sourceAccountType} and account_balance_locked >= #{sourceMoney}")
    int confirmFreezeMoney(AccountDTO accountDTO);

    @Update("update account set account_balance = account_balance + #{targetMoney}" +
            " where user_id = #{userId} and account_type = #{targetAccountType}")
    int confirmExchange(AccountDTO accountDTO);

    @Update("update account set account_balance = account_balance + #{sourceMoney}," +
            "account_balance_locked = account_balance_locked - #{sourceMoney}" +
            " where user_id = #{userId} and account_type = #{sourceAccountType} and account_balance_locked >= #{sourceMoney}")
    int cancelFreezeMoney(AccountDTO accountDTO);

}
