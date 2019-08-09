package com.xyz;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by lc on 2019/8/8.
 **/

public class BindViewField {

    private final VariableElement mVariableElement;
    private final int mViewId;

    public BindViewField(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException("Only field can be annotated with %s" + BindView.class.getSimpleName());
        }
        mVariableElement = (VariableElement) element;
        BindView bindView = mVariableElement.getAnnotation(BindView.class);
        mViewId = bindView.id();
        if (mViewId < 0) {
            throw new IllegalArgumentException("The id must > 0");
        }
    }

    public Name getFieldName() {
        return mVariableElement.getSimpleName();
    }

    public int getViewId() {
        return mViewId;
    }

    public TypeMirror getFieldType() {
        return mVariableElement.asType();
    }
}
