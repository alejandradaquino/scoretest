package scores.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    private static long totalProducedByMachineIn(long machine, long days) {
        return days / machine;
    }

    private static long totalProducedIn(long[] machines, long days) {
        long totalMid = 0;
        for (long machine : machines) {
            totalMid += totalProducedByMachineIn(machine, days);
        }
        return totalMid;
    }

    static long minTime(long[] machines, long goal) {

        Arrays.sort(machines);
        long maxDays = machines[machines.length - 1] * goal;
        long[] days = new long[(int) maxDays];
        for (long machine : machines) {
            int i = (int) machine;
            while (i < maxDays) {
                days[i] += 1;
                i += machine;
            }
        }
        long sum = 0;
        for (int i = 0; i < maxDays; i++) {
            sum += days[i];
            if (sum >= goal) {
                return i;
            }
        }
        return 0;
    }


    static long minTime3(long[] machines, long goal) {
        //Try the binary search arproach.
        Arrays.sort(machines);
        long maxDays = machines[machines.length - 1] * goal;
        long minDays = 0;
        long result = -1L;
        while (minDays < maxDays) {
            long mid = (maxDays + minDays) / 2;
            long total = totalProducedIn(machines, mid);
            if (total < goal) {
                minDays = mid + 1;
            } else {
                result = mid;
                maxDays = mid;
            }
        }
        return result;
    }

    static long minTime2(long[] machines, long goal) {
        long count = 0;
        long days = 0;
        while (count < goal) {
            days++;
            for (int i = 0; i < machines.length; i++) {
                if (days % machines[i] == 0) {
                    count++;
                }
            }
        }
        return days;
//day  1 2 3 4 5 6
//m0   0 1 1 2 2 3
//m1   0 0 1 1 1 2


    }

    public static void main(String[] args) throws IOException {
        File file = new File("src/test/java/scores/services/testFile.txt");

        Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());

        String[] nGoal = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nGoal[0]);

        long goal = Long.parseLong(nGoal[1]);

        long[] machines = new long[n];

        String[] machinesItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            long machinesItem = Long.parseLong(machinesItems[i]);
            machines[i] = machinesItem;
        }

        long ans = minTime(machines, goal);

        System.out.println(ans);
        scanner.close();
    }
}
