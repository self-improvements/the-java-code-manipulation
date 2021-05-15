package io.github.imsejin.processor;

import com.google.auto.service.AutoService;
import io.github.imsejin.annotation.Magic;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
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
            Name elementName = element.getSimpleName();
            if (element.getKind().isInterface()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + elementName);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "Magic annotation is only allowed on interface: " + elementName);
            }
        }

        return true;
    }

}
