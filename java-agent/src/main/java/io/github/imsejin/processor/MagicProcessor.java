package io.github.imsejin.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.imsejin.annotation.Magic;
import lombok.SneakyThrows;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class MagicProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);

        for (Element element : elements) {
            if (element.getKind().isInterface()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + element.getSimpleName());
            } else {
                ClassName className = ClassName.get((TypeElement) element);

                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "Magic annotation is only allowed on interface: " + className.canonicalName());
            }

            ClassName className = ClassName.get((TypeElement) element);
            MethodSpec findNumOfDigits = MethodSpec.methodBuilder("findNumOfDigits")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(int.class)
                    .addParameter(String.class, "content")
                    .addStatement("int count = 0")
                    .beginControlFlow("for (int i = 0; i < content.length(); i++)")
                    .beginControlFlow("if ($T.isDigit(content.charAt(i)))", Character.class)
                    .addStatement("count++")
                    .endControlFlow()
                    .endControlFlow()
                    .addStatement("return count")
                    .build();

            TypeSpec magicalFactoryImpl = TypeSpec.classBuilder(className.simpleName() + "$Impl")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(element.asType())
                    .addMethod(findNumOfDigits)
                    .build();

            JavaFile javaFile = JavaFile.builder(className.packageName(), magicalFactoryImpl).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
                javaFile.writeTo(System.out);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }

        return true;
    }

}
