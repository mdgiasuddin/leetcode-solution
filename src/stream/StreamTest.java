package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamTest {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Giash", "CSE", 1200),
                new Employee(2, "Biplob", "EEE", 1500),
                new Employee(3, "Sobuj", "CSE", 1100),
                new Employee(4, "Jonaed", "EEE", 1700),
                new Employee(5, "Rony", "CSE", 1600),
                new Employee(6, "Raju", "EEE", 1400)
        );

        Map<String, Employee> collect = employees.stream()
                .collect(groupingBy(
                        Employee::getDepartment,
                        collectingAndThen(maxBy(comparingDouble(Employee::getSalary)), Optional::get)
                ));

        employees.stream().collect(groupingBy(Employee::getDepartment));

    }

}
