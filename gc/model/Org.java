package com.accolite.assessment.gc.test.entity;

public class Org{

    private School b;

    public Org(School b) {
        this.b = b;
    }

    public void finalize(){
        System.out.println("Destructor called for Org");
    }
}
