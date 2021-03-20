package org.example.service.impl;

import org.example.constant.CommonConstants;
import org.example.dto.AccountDTO;
import org.example.service.AccountAService;
import org.example.service.ChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class AccountAServiceImpl implements AccountAService {

    @Autowired
    private ChangeService changeService;

    @Override
    public boolean changeMoney(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid) {
       return changeService.changeMoney(buildSourceAccountDTO(money, sourceAccountType, targetAccountType, sourceUid, targetUid));
    }

    @Override
    @Transactional
    public boolean changeMoneyWithTimeout(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid) {
        return changeService.changeMoneyWithConfirmTimeout(buildSourceAccountDTO(money, sourceAccountType, targetAccountType, sourceUid, targetUid));
    }


    private AccountDTO buildSourceAccountDTO(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid) {
        BigDecimal ratio = CommonConstants.CHANGE_RATIO.get(sourceAccountType + "_" + targetAccountType);
        BigDecimal targetMoney = money.multiply(ratio);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(sourceUid);
        accountDTO.setTargeUid(targetUid);
        accountDTO.setSourceAccountType(sourceAccountType);
        accountDTO.setTargetAccountType(targetAccountType);
        accountDTO.setSourceMoney(money);
        accountDTO.setTargetMoney(targetMoney);
        return accountDTO;
    }

}
