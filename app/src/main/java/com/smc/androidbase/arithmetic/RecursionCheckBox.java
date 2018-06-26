package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/13
 * @description 递归找出哪些盒子里装有钥匙
 */
public class RecursionCheckBox {

    public static void main(String args[]) {
        Box firstBox = buildABox("1", false);
        checkBox(firstBox);
    }

    private static void checkBox(Box box) {
        if (box.isHasKey()) {
            System.out.println("box " + box.getName() + "has key");
        }
        List<Box> list = box.getBoxes();
        if(null != list && list.size() > 0){
            for (Box b : list) {
                checkBox(b);
            }
        }
    }

    private static Box buildABox(String name, boolean hasKey) {
        Box aBox = new Box();
        List<Box> list = new ArrayList<>();
        list.add(buildBBox(name + "-1", true));
        list.add(buildBBox(name + "-2", false));
        list.add(buildBBox(name + "-3", false));
        aBox.setHasKey(hasKey);
        aBox.setBoxes(list);
        aBox.setName(name);
        return aBox;
    }

    private static Box buildBBox(String name, boolean hasKey) {
        Box bBox = new Box();
        List<Box> list = new ArrayList<>();
        list.add(buildCBox(name + "-1", false));
        list.add(buildCBox(name + "-2", false));
        list.add(buildCBox(name + "-3", true));
        bBox.setHasKey(hasKey);
        bBox.setBoxes(list);
        bBox.setName(name);
        return bBox;
    }

    private static Box buildCBox(String name, boolean hasKey) {
        Box box = new Box();
        box.setHasKey(hasKey);
        box.setName(name);
        return box;
    }

    /**
     * 盒子中，有盒子，也有钥匙
     */
    static class Box {
        boolean hasKey;
        List<Box> boxes;
        String name;

        public boolean isHasKey() {
            return hasKey;
        }

        public void setHasKey(boolean hasKey) {
            this.hasKey = hasKey;
        }

        public List<Box> getBoxes() {
            return boxes;
        }

        public void setBoxes(List<Box> boxes) {
            this.boxes = boxes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
