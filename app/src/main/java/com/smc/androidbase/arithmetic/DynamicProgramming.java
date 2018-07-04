package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/28
 * @description 动态规划
 */

public class DynamicProgramming {

    public static void main(String[] args) {
//        Cell maxPrice = DynamicProgramming.selectMaxPrice(5);
//        System.out.println("products = " + buildResult(maxPrice));
//        System.out.println("max price = " + maxPrice.getPrice());

        int maxStringSize = findMaxChildString("fosh", "fish");
        System.out.println("maxString length = " + maxStringSize);

        int maxSequenceLength = findMaxChildSequence("fosh", "fish");
        System.out.println("maxSequence length = " + maxSequenceLength);
    }

    /**
     * 寻找最长公共子串（子串，必须是连续的）
     * 两个for循环遍历两个字符串，如果两个字符相等，那么当前最大长度为cell[i-1][j-1]，
     * 如果不相等，那就是0，以此循环，直到最后，最大值，就是最大子串的长度
     *
     * @param mode
     * @param str1
     * @return
     */
    public static int findMaxChildString(String mode, String str1) {
        char[] modeArray = mode.toCharArray();
        char[] str1Array = str1.toCharArray();
        int[][] cell = new int[mode.length()][str1.length()];
        int max = 0;
        for (int i = 0; i < modeArray.length; i++) {
            for (int j = 0; j < str1Array.length; j++) {
                if (modeArray[i] == str1Array[j]) {
                    cell[i][j] = (i - 1 >= 0 && j - 1 >= 0) ? cell[i - 1][j - 1] + 1 : 1;
                    max = cell[i][j] > max ? cell[i][j] : max;
                } else {
                    cell[i][j] = 0;
                }
            }
        }
        return max;
    }

    /**
     * 寻找最长公共子序列（子序列，不一定是连续的）
     * 原理与寻找最长公共子序列类似，只不过如果遇到不相等的情况，当前值为，cell[i][j-1]和cell[i-1][j]中的最大值，
     * 也就是要把之前计算的公共序列长度累加起来
     *
     * @param mode
     * @param str1
     * @return
     */
    public static int findMaxChildSequence(String mode, String str1) {
        char[] modeArray = mode.toCharArray();
        char[] str1Array = str1.toCharArray();
        int[][] cell = new int[mode.length()][str1.length()];
        int max = 0;
        for (int i = 0; i < modeArray.length; i++) {
            for (int j = 0; j < str1Array.length; j++) {
                if (modeArray[i] == str1Array[j]) {
                    cell[i][j] = (i - 1 >= 0 && j - 1 >= 0) ? cell[i - 1][j - 1] + 1 : 1;
                    max = cell[i][j] > max ? cell[i][j] : max;
                } else {
                    cell[i][j] = Math.max(j - 1 >= 0 ? cell[i][j - 1] : 0, i - 1 >= 0 ? cell[i - 1][j] : 0);
                }
            }
        }
        return max;
    }

    /**
     * 使用动态规划的思路，计算出一个4磅容量的袋子怎么样才能装尽可能价值高的物品
     * 物品包括 吉他（1磅-1500元），音响（4磅-3000元），笔记本电脑（3磅-2000元），每个物品只有一个。
     * 先把4磅的袋子分为4个1磅，然后假设先只放吉他，那么第1个1磅放吉他，价值1500，后面几个也是一样
     * 只放入吉他                    1500 1500 1500 1500
     * 再放入吉他和音响，分别是         1500 1500 1500 3000
     * 在放入吉他，音响，笔记本电脑     1500 1500 2000 3500
     * 这样规划的思路就是，每一行获得的最大价值是，当前行物品价格+剩余磅数能产生的最大价值（这个参考上一行的价值）
     * 与 上一行相同磅数作比较，价值更大者就是当前行磅数的最大价值，依次类推，知道所有物品行数，磅数，都遍历完毕为止。
     * 最后一行，最后磅数，的结果，就是袋子所能装最大价值以及装入物品的组合
     *
     * @param maxWeight
     * @return
     */
    public static Cell selectMaxPrice(int maxWeight) {
        List<Product> productList = buildProductSet();
        Cell[][] cells = new Cell[productList.size()][maxWeight];
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < maxWeight; j++) {
                boolean notFirst = i > 0;
                boolean hasLeftWeight = j + 1 > productList.get(i).getWeight();

                int lastPrice = notFirst ? cells[i - 1][j].getPrice() : 0;
                int leftPrice = 0;
                if (notFirst && hasLeftWeight) {
                    leftPrice = cells[i - 1][j - productList.get(i).getWeight()].getPrice();
                }
                int currentEnableProductPrice = j + 1 >= productList.get(i).getWeight() ? productList.get(i).getPrice() : 0;
                int currentPrice = currentEnableProductPrice + leftPrice;
                if (currentPrice > lastPrice) {//当前价格 > 上一行同一列价格
                    List<Product> list = new ArrayList<>();
                    if (notFirst && hasLeftWeight) {
                        list.addAll(cells[i - 1][j - productList.get(i).getWeight()].getList());
                    }
                    list.add(productList.get(i));
                    Cell cell = new Cell(list, currentPrice);
                    cells[i][j] = cell;
                } else {
                    cells[i][j] = cells[i - 1][j];
                }
            }
        }
        Cell maxCell = cells[productList.size() - 1][maxWeight - 1];
        return maxCell;
    }

    private static String buildResult(Cell cell) {
        StringBuilder sb = new StringBuilder();
        if (null != cell) {
            List<Product> list = cell.getList();
            for (Product product : list) {
                sb.append(product.getName());
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private static List<Product> buildProductSet() {
        List<Product> l = new ArrayList<>();
        l.add(new Product("吉他", 1, 1500));
        l.add(new Product("笔记本电脑", 3, 2000));
        l.add(new Product("音响", 4, 3000));
        l.add(new Product("iPhohe", 1, 2000));
        return l;
    }

    static class Cell {
        List<Product> list;
        int price;

        public Cell(List<Product> list, int price) {
            this.list = list;
            this.price = price;
        }

        public List<Product> getList() {
            return list;
        }

        public void setList(List<Product> list) {
            this.list = list;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    static class Product {
        int weight;
        int price;
        String name;

        public Product(String name, int weight, int price) {
            this.weight = weight;
            this.price = price;
            this.name = name;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
