package org.example.service;

import java.math.BigDecimal;

public interface AccountAService {
    boolean changeMoney(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid);

    boolean changeMoneyWithTimeout(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid);
}
