package io.github.imsejin.service;

import io.github.imsejin.model.Money;

/**
 * 프록시 패턴
 *
 * <pre>
 * +--------+      +---------+
 * | Client | ---> | Subject |
 * +--------+      +---------+
 *                      ↑
 *             +-----------------+
 *             |                 |
 *         +-------+      +--------------+
 *         | Proxy | ---> | Real Subject |
 *         +-------+      +--------------+
 * </pre>
 *
 * <ul>
 *     <li>프록시와 리얼 서브젝트가 공유하는 인터페이스가 있고, 클라이언트는 그 인터페이스로 프록시를 사용한다.</li>
 *     <li>클라이언트는 프록시를 거쳐서 리얼 서브젝트를 사용하기에 프록시는 리얼 서브젝트에 대한 접근을 관리하거나, 부가기능을 제공하거나, 리턴 값을 조작할 수 있다.</li>
 *     <li>리얼 서브젝트는 자신의 일에만 집중하고(Single Responsibility Principle), 프록시를 사용하여 부가기능(접근 제한, 로깅, 트랜잭션 등)을 제공할 때 이런 패턴을 주로 사용한다.</li>
 *     <li>부동산을 구매할 때 직접 판매자를 만나지 않고, 중개인을 통해서 부가기능(대리 구매, 연락, 서류 작성, 세금 납부 등)을 제공받는 것과 같다.</li>
 * </ul>
 */
public interface BankService {

    Money lend(double amount);

    void receive(Money money);

}
