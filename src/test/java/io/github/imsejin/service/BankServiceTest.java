package io.github.imsejin.service;

import io.github.imsejin.model.Money;
import io.github.imsejin.service.impl.BankServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class BankServiceTest {

    // Proxy: Java Dynamic Proxy로 인터페이스를 구현하는 프록시 만들기.
    private final BankService bankService = (BankService) Proxy.newProxyInstance(BankService.class.getClassLoader(),
            new Class[]{BankService.class},
            new InvocationHandler() {
                // Read Subject
                private final BankService bankService = new BankServiceImpl();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Before");

                    Object returnValue = method.invoke(bankService, args);

                    System.out.println("After");
                    return returnValue;
                }
            });

    @Test
    void lend() {
        // given
        double amount = 50_000;

        // when
        Money loan = bankService.lend(amount);

        // then
        assertThat(loan.getAmount()).isEqualTo(amount);
    }

    @Test
    void receive() {
        // given
        Money repayment = new Money(50_000);

        // when & then
        bankService.receive(repayment);

        // then
        assertThat(repayment.getAmount()).isEqualTo(0);
    }

}
