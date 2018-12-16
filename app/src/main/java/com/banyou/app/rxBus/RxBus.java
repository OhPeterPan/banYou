package com.banyou.app.rxBus;

import com.banyou.app.annotation.Register;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxBus {
    private static RxBus sRxBus;
    private final CopyOnWriteArrayList<Object> list;

    public RxBus() {
        list = new CopyOnWriteArrayList<>();
    }

    public static RxBus getInstance() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = Holder.rxBus;
                }
            }
        }
        return sRxBus;
    }

    public void register(Object o) {
        if (list != null && !list.contains(o))
            list.add(o);
    }

    public void remove(Object o) {
        if (list != null && list.contains(o))
            list.remove(o);
    }

    public void chain(Function function) {
        Observable.just("")
                .map(function)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o != null) {
                            for (Object object : list) {
                                if (object != null)
                                    send(object, o);
                            }
                        }
                    }
                });
    }

    private void send(Object object, Object o) {
        Method[] methods = object.getClass().getDeclaredMethods();
        try {
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Register.class)) {
                    Class paramType = method.getParameterTypes()[0];

                    if (o.getClass().getName().equals(paramType.getName())) {

                        method.invoke(object, new Object[]{o});
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static RxBus rxBus = new RxBus();
    }
}
