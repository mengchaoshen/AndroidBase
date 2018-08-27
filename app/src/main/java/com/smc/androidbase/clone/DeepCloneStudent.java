package com.smc.androidbase.clone;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/27
 * @description 深拷贝，需要自己重写clone方法，在clone()方法中自己确定哪些类需要重新new
 *
 */

public class DeepCloneStudent implements Cloneable {

    String name;
    int age;
    Professor professor;

    public DeepCloneStudent(String name, int age, Professor professor) {
        this.name = name;
        this.age = age;
        this.professor = professor;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
         DeepCloneStudent o = (DeepCloneStudent) super.clone();
         o.professor = (Professor) professor.clone();
         return o;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Professor professor1 = new Professor();
        professor1.name = "alpha";
        DeepCloneStudent student1 = new DeepCloneStudent("xiaoming", 12, professor1);
        DeepCloneStudent student2 = (DeepCloneStudent) student1.clone();
        student2.professor.name = "beta";

        System.out.println("student1.p = " + student1.professor.name);
        System.out.println("student2.p = " + student2.professor.name);
    }


    public static class Professor implements Cloneable{
        String name;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

}





