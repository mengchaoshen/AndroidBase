package com.smc.androidbase.test;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Streams {

    private enum Status {
        OPEN, CLOSED
    }

    private static final class Task {
        private Status status;
        private Integer points;

        public Task(Status status, Integer points) {
            this.status = status;
            this.points = points;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Integer getPoints() {
            return points;
        }

        public void setPoints(Integer points) {
            this.points = points;
        }

        @Override
        public String toString() {
            return String.format("[%s, %d]", status, points);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws IOException {

        final Collection<Task> tasks = Arrays.asList(
                new Task(Status.OPEN, 5),
                new Task(Status.OPEN, 13),
                new Task(Status.CLOSED, 8)
        );
        final long sum = tasks
                .stream()//构建一个stream
                .filter(task -> task.status == Status.OPEN)//过滤掉status不是OPEN的item
                .mapToInt(Task::getPoints)//把数据转化为int,使用了类的成员方法引用
                .sum();//使用sum方法，计算int总量

        System.out.println("sum = " + sum);

        //并行处理
        final double totalPoints = tasks
                .stream()
                .parallel()//并行处理
                .map(task -> task.getPoints())//转化为point
                .reduce(0, (a, b) -> a + b);//最终计算points的总和
        System.out.println("totalPoints = " + totalPoints);

        final Map<Status, List<Task>> map = tasks
                .stream()
                .collect(Collectors.groupingBy(Task::getStatus));
        System.out.println("map = " + map);

        final Collection<String> result = tasks
                .stream()
                .mapToInt(Task::getPoints)
                .asLongStream()
                .mapToDouble(points -> points / totalPoints)
                .boxed()
                .mapToLong(weight -> (long) (weight * 100))
                .mapToObj(percentage -> percentage + "%")
                .collect(Collectors.toList());

        System.out.println("result = " + result);

        String filename = "";
        final Path path = new File(filename).toPath();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            //onClose当Stream.close被执行的时候会回调
            lines.onClose(() -> System.out.println("Done!")).forEach(System.out::println);
        }
    }

}
