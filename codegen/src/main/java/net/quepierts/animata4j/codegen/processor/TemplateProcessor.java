package net.quepierts.animata4j.codegen.processor;

import com.google.auto.service.AutoService;
import net.quepierts.animata4j.codegen.annotation.Template;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("net.quepierts.animata4j.codegen.annotation.Template")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class TemplateProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Template.class)) {
            String className = ((TypeElement) element).getQualifiedName().toString();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "Skip template class " + className);
        }
        return true;
    }
}
