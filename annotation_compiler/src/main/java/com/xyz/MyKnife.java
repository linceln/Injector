package com.xyz;

/**
 * Created by lc on 2019/8/8.
 **/

public class MyKnife {

    public static void bind(Object host, Object source) {
        try {
            Class<?> clazz = Class.forName(host.getClass().getName() + "$$Injector");
            Injector injector = (Injector) clazz.newInstance();
            injector.inject(host, source);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
