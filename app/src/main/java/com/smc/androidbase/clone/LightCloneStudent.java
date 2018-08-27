package com.smc.androidbase.clone;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/27
 * @description 这里是浅拷贝，也就是说，只会拷贝对象中的基础类型，对象中的引用类型，是直接把引用拷贝过去（而没有去把对象中引用类型重新new一下）
 * 所以在例子中，改变了student2.professor.name = "beta"后，student1.professor.name 的值也会变成"beta"
 */

public class LightCloneStudent implements Cloneable {

    String name;
    int age;
    Professor professor;

    public LightCloneStudent(String name, int age, Professor professor) {
        this.name = name;
        this.age = age;
        this.professor = professor;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Professor professor1 = new Professor();
        professor1.name = "alpha";
        LightCloneStudent student1 = new LightCloneStudent("xiaoming", 12, professor1);
        LightCloneStudent student2 = (LightCloneStudent) student1.clone();
        student2.professor.name = "beta";

        System.out.println("student1.p = " + student1.professor.name);
        System.out.println("student2.p = " + student2.professor.name);
    }


    public static class Professor {
        String name;
    }

}





