package com.smc.androidbase.javabase;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class TestBaseType {

    public static void main(String[] args) {

        //MIN_VALUE = -32768; 最小值是-2^15
        //MAX_VALUE = 32767;  最大值是 2^15-1
        //Short类型的长度是2字节 16位
        Short s;

        //MIN_VALUE = 0x80000000 最小值是-2^31 0x80000000是以补码的形式来表示负数
        //MAX_VALUE = 0x7fffffff 最大值是2^31-1
        //Integer类型是4字节，32位
        Integer integer = 0;
        testInteger();

        //MIN_VALUE = 0x8000000000000000L 最小值是-2^63 0x8000000000000000L以补码的形式来表示负数
        //MAX_VALUE = 0x7fffffffffffffffL 最大值是 2^63-1
        //Long类型长度是8字节 64位
        Long l;

        //正无穷 = 0x7f800000
        //负无穷 = 0xff800000
        //NaN = 0x7fc00000
        //正最大值 = 0x7f7fffff 因为Float的解码取值范围是-126~127所以无法达到0x7fffffff
        //正最小值 = 0x00800000
        //Float类型是4字节 32位
        Float f;
        testFloat();

        //Double类型的长度是8字节64位 符号是1位，阶码是11位，尾数52位
        Double d;


        testBoolean();
        float f1 = 0.1f;
        float sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += f1;
        }
        System.out.println(sum);
        testEqualSignAndEquals();

    }

    private static void testInteger() {
        //装箱
        //Integer i1 = 100; 在编译器里面会调用Integer.valueOf()来进行装箱，
        // 所以等价于 Integer i1 = Integer.valueOf(100);
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 300;
        Integer i4 = 300;
        //拆箱
        //int i = i1 实际上是调用了Integer.intValue()方法获取到Integer里面存储的int值
        int i = i1;
        //这里i1==i2 但是 i3!=i4 因为Integer.valueOf()方法中写了，当int值在[-128, 127]之间时，会在初始化的时候就缓存起来，
        //下次再创建这个值时，会直接从缓存中获取，而100符合缓存的条件，所以i1==i2
        System.out.println("i1==i2 : " + (i1 == i2));//true
        System.out.println("i3==i4 : " + (i3 == i4));//false
    }

    public static void testFloat() {
        int maxValue = Float.floatToIntBits(Float.MAX_VALUE);
        System.out.println("maxValue:" + maxValue);
        int minValue = Float.floatToIntBits(Float.MIN_VALUE);
        System.out.println("minValue:" + minValue);
        int minNormal = Float.floatToIntBits(Float.MIN_NORMAL);
        System.out.println("minNormal:" + minNormal);

        //Float的拆装箱和Integer类似，是调用了Float.valueOf()和Float.floatValue()
        //但是不一样的地方时，Float.valueOf()是直接return new Float()并没有进行缓存，所以f1!=f2，因为浮点型个数是没有穷尽的
        Float f1 = 100.0f;
        Float f2 = 100.0f;
        Float f3 = 200.0f;
        Float f4 = 200.0f;
        System.out.println("f1==f2 : " + (f1 == f2));//false
        System.out.println("f3==f4 : " + (f3 == f4));//false
    }

    private static void testBoolean(){
        //Boolean的装箱想法Boolean.valueOf()直接是判断boolean值，返回已经创建好的TRUE和FALSE对象
        //所以b1==b2 b3==b4
        Boolean b1 = false;
        Boolean b2 = false;
        Boolean b3 = true;
        Boolean b4 = true;
        System.out.println("b1==b2 : " + (b1==b2));//true
        System.out.println("b3==b4 : " + (b3==b4));//true
    }

    private static void testEqualSignAndEquals(){
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        int d = 3;

        Integer e = 322;
        Integer f = 322;

        Long g = 3L;
        long h = 3l;

        Float f1 = 3.0f;
        float f2 = 3.0f;

        //使用"=="进行比较时，
        // 1.如果等号两边都是包装类型，那么比较的是对象本身
        // 2.如果等号两边有一个是基础类型或都是基础类型，那么比较的是类型的值
        // 3.就算等号两边的类型不同，比如int long 只要值相同，结果就是true,
        //   因为使用==比较时，如果类型不同会先强转为高精度类型，然后再进行==比较(查看编译后class文件可知)
        //   比如int == float时，会（float）int == float，也就是把int强转为float进行==比较
        System.out.println((c==(a+b)));//true
        System.out.println(e==f);//false
        System.out.println(c==h);//true
        System.out.println(d==h);//true
        System.out.println(c==f2);//true
        System.out.println(d==f2);//true
        System.out.println(h==f2);//true
        System.out.println(f1==f2);//true

        //当使用equals()方法进行比较时，比较的是包装类的值
        //但是里面定义了，传入值类型不是Integer类型时，直接返回false
        System.out.println(c.equals(g));//false
        //System.out.println(c==g);//编译错误
        System.out.println(g==h);//true




    }
}
