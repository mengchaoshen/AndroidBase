package com.smc.androidbase.test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Car {

    public static void main(String[] args) {

        //1.使用Car::new构造方法引用的方式，创建实例
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        //2.类的静态方法引用，可以有一个方法的传入
        cars.forEach(Car::collide);
        //3.类的成员方法引用 没有定义参数
        cars.forEach(Car::repair);

        final Car police = Car.create(Car::new);
        //4.对象的成员方法引用 接受一个参数
        cars.forEach(police::follow);
    }

    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("collide " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("follow " + another.toString());
    }

    public void repair() {
        System.out.println("repair " + this.toString());
    }
}
