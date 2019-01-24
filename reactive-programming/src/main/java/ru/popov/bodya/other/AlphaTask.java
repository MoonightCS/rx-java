package ru.popov.bodya.other;

public class AlphaTask {

    private int max = 0;

    /**
     * Дано: [1, 6, 9, 4, 2]
     * Результат: 12
     * Ответ: Максимальное количество сока удастся получить, если сорвать первый лист (1), третий лист (9) и пятый лист (2), всего 1 + 9 + 2 = 12.
     */

    public static void main(String[] args) {
        AlphaTask task = new AlphaTask();
        System.out.println(task.gatherJuice(new int[]{900, 1000, 900, 1, 900}));
        System.out.println(task.gatherJuice(new int[]{100, 10, 1, 100}));
        System.out.println(task.gatherJuice(new int[]{1, 6, 9, 4, 2}));
        System.out.println(task.gatherJuice(new int[]{1, 2, 3, 4}));
        System.out.println(task.gatherJuice(new int[]{1, 2, 3, 4}));
    }

    //
    int gatherJuice(int[] leaves) {
        return Math.max(
                calc(leaves, 0, 0),
                calc(leaves, 1, 0)
        );
    }

    private int calc(int[] arr, int i, int sum) {
        if (i > arr.length - 1) {
            return 0;
        }
        if (i >= arr.length - 2) {
            return sum + arr[i];
        }
        int max1 = calc(arr, i + 2, sum + arr[i]);
        int max2 = calc(arr, i + 3, sum + arr[i]);
        if (max1 >= max2) {
            return max1;
        } else {
            return max2;
        }
    }
}
