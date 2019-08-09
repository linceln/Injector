package com.xyz;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by lc on 2019/8/8.
 **/
public class LocalColumnProcessor extends AbstractProcessor {

    private Elements mElements;
    private Messager mMessager;
    private Filer mFiler;

    private Map<String, AnnotatedClass> map = new HashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElements = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        try {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                String fullClassName = enclosingElement.getQualifiedName().toString();
                AnnotatedClass annotatedClass = map.get(fullClassName);
                if (annotatedClass == null) {
                    annotatedClass = new AnnotatedClass(enclosingElement, mElements);
                    map.put(fullClassName, annotatedClass);
                }
                BindViewField bindViewField = new BindViewField(element);
                annotatedClass.addFiled(bindViewField);
            }
        } catch (IllegalArgumentException e) {
            return true;
        }

        for (AnnotatedClass annotatedClass : map.values()) {
            try {
                annotatedClass.generateClass().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }
}
