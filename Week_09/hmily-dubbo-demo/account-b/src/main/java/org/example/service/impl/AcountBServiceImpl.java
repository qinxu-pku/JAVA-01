package org.example.service.impl;

import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.example.api.AccountService;
import org.example.dto.AccountDTO;
import org.example.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("acountBService")
public class AcountBServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean changeMoney(AccountDTO accountDTO) {
        int i = accountMapper.freezeMoney(accountDTO);
        if (i < 1) {
            throw new HmilyRuntimeException("账户异常，余额不足");
        }
        return true;
    }


    @Override
    @HmilyTCC(confirmMethod = "confirmTimeout", cancelMethod = "cancel")
    @Transactional
    public boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO) {
        int i = accountMapper.freezeMoney(accountDTO);
        if (i < 1) {
            throw new HmilyRuntimeException("账户异常，余额不足");
        }
        return true;
    }

    @Transactional
    public boolean confirmTimeout(AccountDTO accountDTO) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accountMapper.confirmFreezeMoney(accountDTO);
        accountMapper.confirmExchange(accountDTO);
        return true;
    }

    public boolean cancel(AccountDTO accountDTO) {
        accountMapper.cancelFreezeMoney(accountDTO);
        return true;
    }

    public boolean confirm(AccountDTO accountDTO) {
        accountMapper.confirmFreezeMoney(accountDTO);
        accountMapper.confirmExchange(accountDTO);
        return true;
    }
}
