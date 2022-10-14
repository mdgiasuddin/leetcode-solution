package dfs;

import java.util.*;

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
}

public class DFSSolution3 {

    // Leetcode problem: 690
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> employeeMap = new HashMap<>();

        for (Employee employee : employees) {
            employeeMap.put(employee.id, employee);
        }

        return dfsSubordinate(id, employeeMap);
    }

    private int dfsSubordinate(int id, Map<Integer, Employee> employeeMap) {

        int res = employeeMap.get(id).importance;
        for (int subordinate : employeeMap.get(id).subordinates) {
            res += dfsSubordinate(subordinate, employeeMap);
        }

        return res;
    }

    // Leetcode problem: 841
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> visited = new HashSet<>();
        dfsRoom(0, rooms, visited);

        return visited.size() == rooms.size();
    }

    public void dfsRoom(int room, List<List<Integer>> rooms, Set<Integer> visited) {
        if (visited.contains(room)) {
            return;
        }

        visited.add(room);
        for (int neighbor : rooms.get(room)) {
            dfsRoom(neighbor, rooms, visited);
        }
    }
}
