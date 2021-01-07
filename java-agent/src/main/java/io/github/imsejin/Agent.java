package io.github.imsejin;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.implementation.FixedValue.value;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 바이트코드를 조작하는 여러가지 라이브러리
 *
 * @see <a href="https://asm.ow2.io/">ASM</a>
 * @see <a href="https://www.javassist.org/">Javassist</a>
 * @see <a href="https://bytebuddy.net/">ByteBuddy</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.instrument/java/lang/instrument/package-summary.html">Manifest Attributes</a>
 */
public class Agent {

    /**
     * @see <a href="https://www.infoq.com/articles/Easily-Create-Java-Agents-with-ByteBuddy/">Easily Create Java Agents with ByteBuddy</a>
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        AgentBuilder.Default agentBuilder = new AgentBuilder.Default();

        agentBuilder.type(any())
                .transform((builder, typeDescription, classLoader, javaModule) -> builder
                        .method(named("getContent"))
                        .intercept(value("This is a content.")))
                .installOn(inst);
    }

}
