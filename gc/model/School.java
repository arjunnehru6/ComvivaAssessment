package com.accolite.assessment.gc.test.entity;

public class School{

    public void finalize(){
        System.out.println("Destructor called for Dept");
    }

}
