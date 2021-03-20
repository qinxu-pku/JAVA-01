package org.example.service;


import org.example.dto.AccountDTO;

public interface ChangeService {
    boolean changeMoney(AccountDTO accountDTO);

    boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO);
}
