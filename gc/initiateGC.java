package com.accolite.assessment.gc;

import java.util.HashSet;
import java.util.Set;

public class initiateGC implements Runnable{

    private objRef root;
    private Set<Integer> releaseObjects;
    private Queue referenceQueue;

    public initiateGC(objRef root, Set<Integer> releaseObjects, Queue referenceQueue) {
        this.root = root;
        this.releaseObjects = releaseObjects;
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run() {
        System.out.println("Initiating GC Task");
        Set<Integer> markSet = new HashSet<>();
        markReference(root, markSet);
        removeReference(root, markSet);
    }

    private objRef removeReference(objRef root, Set<Integer> markSet) {
        Object obj = root.getObject();

        int identityHashCode = System.identityHashCode(obj);

        Set<objRef> deleteReferences = new HashSet<>();
        for (objRef reference : root.getReferences()){
            if (removeReference(reference, markSet) == null)
                deleteReferences.add(reference);
        }

        addToReferenceQueue(deleteReferences);

        root.getReferences().removeAll(deleteReferences);

        if (markSet.contains(identityHashCode))
            return root;
        return null;
    }

    private void addToReferenceQueue(Set<objRef> deleteReferences) {
        for (objRef reference : deleteReferences){
            try {
                if (reference.getObject().getClass().getDeclaredMethod("finalize") == null)
                    continue;
                referenceQueue.add(reference.getObject());
            } catch (NoSuchMethodException e) {
                System.out.println("Finalize method does not exist");
            }
        }
    }

    private void markReference(objRef root, Set<Integer> markSet) {

        Object obj = root.getObject();

        int identityHashCode = System.identityHashCode(obj);
        if (releaseObjects.contains(identityHashCode))
            return;
        else if (! markSet.add(identityHashCode))
            return;


        for (objRef reference : root.getReferences()){
            markReference(reference, markSet);
        }
    }
}
