package com.accolite.assessment.gc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Finalize<T> implements Runnable {

    private Queue<T> referenceQueue;

    public Finalize(Queue<T> referenceQueue){
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run() {
        System.out.println("Inside Finalize");

        while (true){
            T object = referenceQueue.remove();
            try {
                Method finalize = object.getClass().getDeclaredMethod("finalize");

                if (finalize == null)
                    continue;

                finalize.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
