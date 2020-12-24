package io.github.imsejin;

/**
 * Hello world!
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-5.html">Loading, Linking, and Initializing</a>
 * @see <a href="https://javacan.tistory.com/entry/1">동적인 클래스 로딩과 클래스로더</a>
 */
public class App {

    public static void main(String[] args) {
        printClassLoaders();
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

        System.out.println(classLoader); // ClassLoaders$AppClassLoader
        System.out.println(classLoader.getParent()); // ClassLoaders$PlatformClassLoader
        System.out.println(classLoader.getParent().getParent()); // null
    }

}
