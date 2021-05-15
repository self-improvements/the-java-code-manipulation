package io.github.imsejin.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Money {

    private double amount;

    public Money(double amount) {
        this.amount = amount;
    }

}
