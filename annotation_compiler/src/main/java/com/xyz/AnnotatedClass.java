package com.xyz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by lc on 2019/8/8.
 **/
public class AnnotatedClass {

    private TypeElement mTypeElement;
    private Elements mElements;
    private List<BindViewField> mBindViewFields = new LinkedList<>();

    public AnnotatedClass(TypeElement typeElement, Elements elements) {
        mTypeElement = typeElement;
        mElements = elements;
    }

    public void addFiled(BindViewField field) {
        mBindViewFields.add(field);
    }

    public JavaFile generateClass() {
        String packageName = getPackageName(mTypeElement);
        String className = getClassName(mTypeElement, packageName);
        ClassName bindClassName = ClassName.get(packageName, className);
        TypeSpec finderClass = TypeSpec.classBuilder(bindClassName.simpleName() + "$$Injector")
                .addModifiers(Modifier.PUBLIC)
//                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("com.xyz", "Injector"), TypeName.get(mTypeElement.asType())))
//                .addMethod(builder.build())
                .build();
        return JavaFile.builder(packageName, finderClass).build();
    }

    private String getPackageName(TypeElement type) {
        return mElements.getPackageOf(type).getQualifiedName().toString();
    }

    private String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }
}
