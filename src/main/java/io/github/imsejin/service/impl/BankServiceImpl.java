package io.github.imsejin.service.impl;

import io.github.imsejin.model.Money;
import io.github.imsejin.service.BankService;

public class BankServiceImpl implements BankService {

    @Override
    public Money lend(double amount) {
        return new Money(amount);
    }

    @Override
    public void receive(Money money) {
        money.setAmount(0);
        System.out.printf("Successfully repaid %,f\n", money.getAmount());
    }

}
