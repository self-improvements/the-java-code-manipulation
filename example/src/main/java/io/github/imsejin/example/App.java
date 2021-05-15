package io.github.imsejin.example;

import io.github.imsejin.annotation.Magic;
import io.github.imsejin.example.model.Book;

import java.util.Arrays;

/**
 * Application
 *
 * <p> JVM 구조
 * <pre>
 *                +------------Class Loader System-------------+
 *                | +---------+  +---------+  +--------------+ |
 *                | |         |  |         |  |              | |
 *                | | Loading ---> Linking ---> Initializing | |
 *                | |         |  |         |  |              | |
 *                | +---------+  +---------+  +--------------+ |
 *                +----------------------+---------------------+
 *                                       |
 *                                       |
 *                                       |
 * +----------------------------------Memory-------------------------------------+
 * |            +---------------------+  +---------------+                       |
 * | +-------+  |                     |  |               |  +------+  +--------+ |
 * | |       |  | PC(Program Counter) |  | Native Method |  |      |  |        | |
 * | | Stack --->      Registers      --->     Stack     ---> Heap ---> Method | |
 * | |       |  |                     |  |               |  |      |  |        | |
 * | +-------+  +---------------------+  +---------------+  +------+  +--------+ |
 * +--------------------------------------+--------------------------------------+
 *                                        |
 *                         +---------------------------------+
 *                         |                                 |
 * +---------------Execution Engine----------------+   +-----|-----+ +-----------+
 * | +-------------+  +--------------+  +--------+ |   |           | |           |
 * | |             |  |              |  |        | |   |   Java    | |  Native   |
 * | | Interpreter ---> JIT Compiler --->   GC   | -----  Native   ---  Method   |
 * | |             |  |              |  |        | |   | Interface | | Libraries |
 * | +-------------+  +--------------+  +--------+ |   |           | |           |
 * +-----------------------------------------------+   +-----------+ +-----------+
 * </pre>
 *
 * <h2>Memory</h2>
 * <dl>
 *     <dt>Stack</dt>
 *     <dd>쓰레드마다 런타임 스택을 생성함. 그 안에 메서드 호출을 Stack Frame이라는 Block으로 쌓는다. 쓰레드가 종료되면 스택도 사라진다.</dd>
 *
 *     <dt>PC Registers</dt>
 *     <dd>쓰레드마다 쓰레드 내 현재 실행할 Stack Frame을 가리키는 포인터가 생성되는 곳.</dd>
 *
 *     <dt>Native Method Stack</dt>
 *     <dd>JNI의 메서드를 호출할 때마다 생성하는 런타임 스택을 저장하는 곳.</dd>
 *
 *     <dt>Method</dt>
 *     <dd>클래스 정보(클래스명, 부모 클래스명, 메서드, 변수 등)를 저장/공유하는 곳.</dd>
 *
 *     <dt>Heap</dt>
 *     <dd>객체를 저장/공유하는 곳.</dd>
 * </dl>
 *
 * <h2>Execution Engine</h2>
 * <dl>
 *     <dt>Interpreter</dt>
 *     <dd>바이트코드를 한 줄씩 실행.</dd>
 *
 *     <dt>Just In Time Compiler</dt>
 *     <dd>인터프리터 효율을 높이기 위해, 인터프리터가 중복 코드를 발견하면 JIT 컴파일러로 이를 native code로 변경한다. 이후부터 인터프러터는 해당 native code로 컴파일된 코드를 바로 사용한다.</dd>
 *
 *     <dt>Garbage Collector</dt>
 *     <dd>참조하지 않는 객체를 모아서 정리하여 시스템에 메모리를 반환한다.</dd>
 * </dl>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-5.html">Loading, Linking, and Initializing</a>
 * @see <a href="https://javacan.tistory.com/entry/1">동적인 클래스 로딩과 클래스로더</a>
 */
@Magic
public class App {

    public static void main(String[] args) {
        printClassLoaders();

        // null이어야 하는데 java-agent가 그 값을 변경했다.
        // -javaagent:./the-java-code-manipulation/java-agent/target/java-agent-0.1.0.jar
        System.out.println(new Book().getContent());

        reflection(Book.class);
    }

    /*
     * 클래스로더는 계층적 구조로 이뤄져 있다. 기본적으로 3개의 로더를 제공한다.
     * 클래스 로더는 Loading -> Linking -> Initializing 순으로 진행된다.
     *
     * ---
     *
     * [Loading]
     * 1. Bootstrap Class Loader:   JAVA_HOME/lib에 있는 코어 자바 API를 제공한다. 최상위 우선순위를 가진 클래스 로더.
     * 2. Platform Class Loader:    JAVA_HOME/lib/ext 폴더 또는 java.ext.dirs 시스템 변수에 해당하는
     *                              위치에 있는 클래스를 읽는다. (구 Extension Class Loader)
     * 3. Application Class Loader: application classpath(애플리케이션을 실행할 때 주는 -classpath 옵션 또는
     *                              java.class.path 환경 변수의 값에 해당하는 위치)에서 클래스를 읽는다.
     *
     * 클래스를 로딩할 때 최상위부터 시작하여 해당 클래스 정보를 찾는다. 이를 ClassLoader Delegation Model이라고 한다.
     * 최하위 클래스 로더까지 해당 클래스 정보를 찾지 못하면 ClassNotFoundException 예외가 발생한다.
     *
     * ---
     *
     * [Linking]
     * Verification -> Preparation -> Resolution 순으로 진행된다.
     *
     * 1. Verification: bytecode 파일의 유효성을 체크한다.
     * 2. Preparation:  static 변수와 기본값에 필요한 메모리를 준비한다.
     * 3. Resolution:   symbolic memory reference를 메서드 영역에 있는 실제 reference로 교체한다. (optional)
     */
    private static void printClassLoaders() {
        ClassLoader classLoader = App.class.getClassLoader();

        System.out.printf("Application Class Loader: %s\n", classLoader); // ClassLoaders$AppClassLoader
        System.out.printf("Platform Class Loader: %s\n", classLoader.getParent()); // ClassLoaders$PlatformClassLoader
        System.out.printf("Bootstrap Class Loader: %s\n", classLoader.getParent().getParent()); // null
    }

    private static <T> void reflection(Class<T> clazz) {
        Arrays.stream(clazz.getDeclaredFields()).forEach(System.out::println);
    }

}
