package com.accolite.assessment.gc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class garbageCollector {

    private static objRef root = new objRef(new Object());
    private static Map<Integer, objRef> allReferences = new HashMap<>();
    private static ExecutorService gcExecutor = Executors.newSingleThreadExecutor();
    private static ExecutorService finalizeExecutor = Executors.newSingleThreadExecutor();
    private static Queue<Object> referenceQueue = new Queue<>();
    private static Set<Integer> releaseObjects = new HashSet<>();

    private garbageCollector() {}


    static {
        Finalize<Object> finalizeTask = new Finalize<>(referenceQueue);
        finalizeExecutor.submit(finalizeTask);
    }


    public static objRef get(Object object){
        return createReferences(object, 0);
    }

    private static objRef createReferences(Object object, int count) {

        if (object == null)
            return null;

        int hashCode = System.identityHashCode(object);
        objRef reference = allReferences.get(hashCode);
        if (reference == null)
            reference = new objRef(object);

        if (count == 0)
            root.addReference(reference);

        for (Field field : object.getClass().getDeclaredFields()){
            if (field instanceof Object){
                try {
                    field.setAccessible(true);
                    reference.addReference(createReferences(field.get(object), ++count));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return reference;
    }

    public static synchronized void release(Object object){
        if (object == null)
            return;
        releaseObjects.add(System.identityHashCode(object));
    }

    public static synchronized void gc(){
        gcExecutor.submit(new initiateGC(root, new HashSet<Integer>(releaseObjects), referenceQueue));
        releaseObjects.clear();
    }

}
