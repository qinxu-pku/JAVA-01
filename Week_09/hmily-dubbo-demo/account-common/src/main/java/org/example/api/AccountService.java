package org.example.api;

import org.example.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;

public interface AccountService {

    @Hmily
    boolean changeMoney(AccountDTO accountDTO);

    @Hmily
    boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO);
}
