package streams;

import java.util.*;
import java.util.stream.Collectors;

public class Employee {

    private int employeeId;
    private double salary;
    private String department;

    public Employee(int employeeId, double salary, String department) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.department = department;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}

 class EmployeeStreamExample{

    public static void main(String[] args){

        List<Employee> employee= Arrays.asList(
               new Employee(1001,30000,"software engineer"),
                new Employee(1006,25000,"software developer"),
                new Employee(1007,35000,"software engineer"),
                new Employee(1002,40000,"devops"),
                new Employee(1005, 50000.0, "HR"),
                new Employee(1090, 60000.0, "IT"),
                new Employee(1067, 70000.0, "IT"),
                new Employee(1985, 55000.0, "HR"),
                new Employee(1748, 80000.0, "Finance"),
                new Employee(1003,50000,"project management")
        );

        //this will get the details of all the departments who are in it department
        List<Employee> empl=employee.stream()
                .filter(s->"IT".equals(s.getDepartment()))
                .collect(Collectors.toList());
        System.out.println("the list of all the employee of department who are in it: "+empl);



        //this will get the list of employeees who are in software engineer
        List<Employee> listItDepartment=employee.stream().filter(e-> "software engineer"
                .equals(e.getDepartment())).collect(Collectors.toList());
        System.out.println("the list of employee who are in software engineer : "+listItDepartment);



        //this will get the sum of all the employee salary
        double emp1=employee.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println("the sum of all the salary of the employee: "+emp1);


        //this will get the average salary of all the employees
        double avgsalary=employee.stream()
                        .mapToDouble(Employee::getSalary)
                        .average()
                                .orElse(0);
        System.out.println("average salary of all the emlpoyees are : "+avgsalary);
        

        //this will get the details whose salary is greater than 60000
        List<Employee> maxsal=employee.stream().filter(emp->emp.getSalary()>60000).collect(Collectors.toList());
        System.out.println("list of employees with salary greater than 60000 are : "+maxsal);


        //employee with salary greater than 50000
        List<Employee> employeeWithHighSalary=employee.stream().filter(e->e.getSalary()>50000).collect(Collectors
                .toList());
        System.out.println("employee with salary greater than 50000 are: "+employeeWithHighSalary);


        //employee with salary greater than 60000 and in it department
        List<Employee> employeeWithHighSalaryInIT=employee.stream().filter(e->e.getDepartment()
                .equals("IT") && e.getSalary()>60000).collect(Collectors.toList());
        System.out.println("employee with salary greater than 60000 and in it department are : "+employeeWithHighSalaryInIT);


        //list of employee with salary greater than 30000 and in software engineer
        List<Employee> employeeWithSalaryGreaterInSoftwareEngineer=employee.stream().filter(s->s.getDepartment()
                .equals("software engineer") && s.getSalary()>30000).collect(Collectors.toList());
        System.out.println("list of employee with salary more than 30000 and in software engineer : "
                +employeeWithSalaryGreaterInSoftwareEngineer);



        //the average salary of all employees in the "IT" department
        double averageSalaryIT=employee.stream().filter(e->e.getDepartment().equals("IT"))
                .mapToDouble(e->e.getSalary()).average().orElse(0);
        System.out.println("average salary of it is : "+averageSalaryIT);


        //Find the maximum salary of all employees
        double maxSalary = employee.stream()
                .mapToDouble(e -> e.getSalary())
                .max()
                .orElse(0);
        System.out.println("Max salary: " + maxSalary);



// Find all employees with a department of "HR" or "IT"
        List<Employee> employeesHRorIT = employee.stream()
                .filter(e -> e.getDepartment().equals("HR") || e.getDepartment().equals("IT"))
                .collect(Collectors.toList());
        System.out.println(employeesHRorIT);



       // Find the sum of salaries of all employees in the "software engineer" department
        double sumSalarySoftwareEngineer = employee.stream()
                .filter(e -> e.getDepartment().equals("software engineer"))
                .mapToDouble(e -> e.getSalary())
                .sum();
        System.out.println("Sum salary software engineer: " + sumSalarySoftwareEngineer);


       // Find the employee with the highest salary in the "IT" department
        Employee highestPaidIT = employee.stream()
                .filter(e -> e.getDepartment().equals("IT"))
                .max(Comparator.comparingDouble(e -> e.getSalary()))
                .orElse(null);
        System.out.println("Highest paid IT: " + highestPaidIT);


        //find the employee with the highest salary in software engineer department
        Employee highestPaidSoftwareEngineer=employee.stream().filter(e->e.getDepartment()
                .equals("software engineer"))
                .max(Comparator.comparingDouble(e->e.getSalary()))
                .orElse(null);
        System.out.println("employee with highest salary in software engineer : "+highestPaidSoftwareEngineer);



        //Find all employees with a salary greater than 50000 and department of "HR" or "IT"
        List<Employee> employeesHighSalaryHRorIT = employee.stream()
                .filter(e -> e.getSalary() > 50000 && (e.getDepartment().equals("HR") || e.getDepartment().equals("IT")))
                .collect(Collectors.toList());
        System.out.println(employeesHighSalaryHRorIT);


        //Find the average salary of all employees in the "HR" department
        double averageSalaryHR = employee.stream()
                .filter(e -> e.getDepartment().equals("HR"))
                .mapToDouble(e -> e.getSalary())
                .average()
                .orElse(0);
        System.out.println("Average salary HR: " + averageSalaryHR);




        // Filter employees in the HR department and collect them into a list
        List<Employee> hrEmployees = employee.stream()
                .filter(e -> e.getDepartment().equals("HR"))
                .collect(Collectors.toList());
        // Print the details of employees in the HR department
        hrEmployees.forEach(System.out::println);
        // Calculate the average salary of HR employees
        double averageSalaryHR1 = hrEmployees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
        // Print the average salary of HR employees
        System.out.println("Average salary of the HR: " + averageSalaryHR1);




        //Find the maximum salary of all employees in the "software engineer" department
        double maxSalarySoftwareEngineer = employee.stream()
                .filter(e -> e.getDepartment().equals("software engineer"))
                .mapToDouble(e -> e.getSalary())
                .max()
                .orElse(0);
        System.out.println("Max salary software engineer: " + maxSalarySoftwareEngineer);



        // Find all employees with a department of "Finance" or "project management"
        List<Employee> employeesFinanceorProjectManagement = employee.stream()
                .filter(e -> e.getDepartment().equals("Finance") || e.getDepartment().equals("project management"))
                .collect(Collectors.toList());
        System.out.println(employeesFinanceorProjectManagement);



        //Find the number of employees in each department
        Map<String, Long> employeesByDepartment = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println(employeesByDepartment);



        //Find the average salary of employees in each department
        Map<String, Double> averageSalaryByDepartment = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(averageSalaryByDepartment);


       // Find the employee with the highest salary in each department
        Map<String, Optional<Employee>> highestPaidEmployeeByDepartment = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));
        System.out.println(highestPaidEmployeeByDepartment);


        //Find the sum of salaries of employees in each department
        Map<String, Double> sumSalaryByDepartment = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)));
        System.out.println(sumSalaryByDepartment);


        //Find the employees with a salary greater than the average salary in each department
        Map<String, List<Employee>> employeesAboveAverageSalaryByDepartment = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.filtering(e -> e.getSalary() > employee.stream().filter(f -> f.getDepartment().equals(e.getDepartment())).mapToDouble(Employee::getSalary).average().orElse(0), Collectors.toList())));
        System.out.println(employeesAboveAverageSalaryByDepartment);


        //Find the department with the highest average salary
        String departmentWithHighestAverageSalary = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(e -> e.getValue()))
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println(departmentWithHighestAverageSalary);




        //Find the department with the lowest average salary
        String departmentWithLowestAverageSalary = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .min(Comparator.comparingDouble(e -> e.getValue()))
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println(departmentWithLowestAverageSalary);



       // Find the employees with a salary greater than 50000 in each department
        Map<String, List<Employee>> employeesWithHighSalaryByDepartment = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.filtering(e -> e.getSalary() > 50000, Collectors.toList())));
        System.out.println(employeesWithHighSalaryByDepartment);




        //Find the average salary of employees in each department, excluding the highest and lowest salaries
        Map<String, Double> averageSalaryByDepartmentExcludingExtremes = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(e -> {
                    List<Double> salaries = employee.stream()
                            .filter(f -> f.getDepartment().equals(e.getDepartment()))
                            .map(Employee::getSalary)
                            .sorted()
                            .skip(1)
                            .limit(employee.stream().filter(f -> f.getDepartment().equals(e.getDepartment())).count() - 2)
                            .collect(Collectors.toList());
                    return salaries.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                })));
        System.out.println(averageSalaryByDepartmentExcludingExtremes);



        //Find the department with the most employees with a salary greater than 50000
        String departmentWithMostHighSalaryEmployees = employee.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.filtering(e -> e.getSalary() > 50000, Collectors.counting())))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println(departmentWithMostHighSalaryEmployees);








        if(employee.equals("IT")) {
            List<Employee> maxsal1 = employee.stream().filter(emp -> emp.getSalary() > 60000).collect(Collectors.toList());
            System.out.println(maxsal1);
        }else{
            List<Employee> maxsal2 = employee.stream().filter(emp -> emp.getSalary() < 60000).collect(Collectors.toList());
            System.out.println(maxsal2);
        }



        System.out.println("IT employee are :"+empl);
        System.out.println("sum of employee salary are: "+emp1);

    }
}
