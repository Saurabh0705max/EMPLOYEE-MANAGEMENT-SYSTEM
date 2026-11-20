import java.util.*;

class Employee {
    int id;
    String name;
    String department;
    String designation;
    double salary;
    int attendance;
    float performance;

    Employee(int id, String name, String department,
             String designation, double salary,
             int attendance, float performance) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.attendance = attendance;
        this.performance = performance;
    }
}

class Node {
    Employee data;
    Node next;

    Node(Employee data) {
        this.data = data;
        this.next = null;
    }
}

class LeaveRequest {
    int employeeId;
    String employeeName;
    String reason;

    LeaveRequest(int employeeId, String employeeName, String reason) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.reason = reason;
    }
}

class EmployeeManagementSystem {
    private Node head;
    private final Queue<LeaveRequest> leaveQueue;
    private final Stack<String> actionStack;
    private int nextId;
    private final Scanner sc;

    EmployeeManagementSystem() {
        head = null;
        leaveQueue = new LinkedList<>();
        actionStack = new Stack<>();
        nextId = 1001;
        sc = new Scanner(System.in);
    }

    // Input helper methods
    private String readLine(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    private int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readLine(message).trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(readLine(message).trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private float readFloat(String message) {
        while (true) {
            try {
                return Float.parseFloat(readLine(message).trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid rating.");
            }
        }
    }

    // Add employee at the end of the linked list
    public void addEmployee() {
        String name = readLine("\nEnter employee name: ");
        String department = readLine("Enter department: ");
        String designation = readLine("Enter designation: ");
        double salary = readDouble("Enter salary: ");
        int attendance = readInt("Enter attendance percentage: ");
        float performance = readFloat("Enter performance rating out of 5: ");

        Employee emp = new Employee(
            nextId++, name, department, designation,
            salary, attendance, performance
        );

        Node newNode = new Node(emp);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        actionStack.push("Added employee ID " + emp.id);

        System.out.println("\nEmployee added successfully.");
        System.out.println("Employee ID: " + emp.id);
    }

    // Search employee by ID
    private Node searchEmployeeById(int id) {
        Node current = head;

        while (current != null) {
            if (current.data.id == id) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    // Display one employee
    private void displayEmployee(Employee emp) {
        System.out.println("\n----------------------------------------");
        System.out.println("Employee ID    : " + emp.id);
        System.out.println("Name           : " + emp.name);
        System.out.println("Department     : " + emp.department);
        System.out.println("Designation    : " + emp.designation);
        System.out.printf("Salary         : Rs. %.2f%n", emp.salary);
        System.out.println("Attendance     : " + emp.attendance + "%");
        System.out.println("Performance    : " + emp.performance + "/5");
        System.out.println("----------------------------------------");
    }

    // Display all employees
    public void displayAllEmployees() {
        if (head == null) {
            System.out.println("\nNo employees found.");
            return;
        }

        Node current = head;
        System.out.println("\n========== EMPLOYEE LIST ==========");

        while (current != null) {
            displayEmployee(current.data);
            current = current.next;
        }
    }

    // Search by ID or name
    public void searchEmployee() {
        System.out.println("\n1. Search by ID");
        System.out.println("2. Search by Name");

        int choice = readInt("Enter choice: ");

        if (choice == 1) {
            int id = readInt("Enter employee ID: ");
            Node employee = searchEmployeeById(id);

            if (employee != null) {
                displayEmployee(employee.data);
            } else {
                System.out.println("\nEmployee not found.");
            }
        } else if (choice == 2) {
            String name = readLine("Enter employee name: ");
            boolean found = false;

            Node current = head;

            while (current != null) {
                if (current.data.name.equalsIgnoreCase(name)) {
                    displayEmployee(current.data);
                    found = true;
                }

                current = current.next;
            }

            if (!found) {
                System.out.println("\nEmployee not found.");
            }
        } else {
            System.out.println("\nInvalid choice.");
        }
    }

    // Update employee details
    public void updateEmployee() {
        int id = readInt("\nEnter employee ID to update: ");
        Node employee = searchEmployeeById(id);

        if (employee == null) {
            System.out.println("\nEmployee not found.");
            return;
        }

        Employee emp = employee.data;

        emp.name = readLine("Enter new name: ");
        emp.department = readLine("Enter new department: ");
        emp.designation = readLine("Enter new designation: ");
        emp.salary = readDouble("Enter new salary: ");
        emp.attendance = readInt("Enter new attendance: ");
        emp.performance = readFloat("Enter new performance rating: ");

        actionStack.push("Updated employee ID " + id);

        System.out.println("\nEmployee updated successfully.");
    }

    // Delete employee from linked list
    public void deleteEmployee() {
        int id = readInt("\nEnter employee ID to delete: ");

        if (head == null) {
            System.out.println("\nNo employees found.");
            return;
        }

        Node current = head;
        Node previous = null;

        while (current != null && current.data.id != id) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("\nEmployee not found.");
            return;
        }

        if (previous == null) {
            head = current.next;
        } else {
            previous.next = current.next;
        }

        actionStack.push("Deleted employee ID " + id);

        System.out.println("\nEmployee deleted successfully.");
    }

    // Sort employees by salary (highest first)
    public void sortBySalary() {
        List<Employee> employees = new ArrayList<>();
        Node current = head;

        while (current != null) {
            employees.add(current.data);
            current = current.next;
        }

        employees.sort(
            (a, b) -> Double.compare(b.salary, a.salary)
        );

        System.out.println("\n========== SORTED BY SALARY ==========");

        for (Employee emp : employees) {
            displayEmployee(emp);
        }

        System.out.println("Time Complexity: O(N log N)");
    }

    // Sort employees alphabetically by name
    public void sortByName() {
        List<Employee> employees = new ArrayList<>();
        Node current = head;

        while (current != null) {
            employees.add(current.data);
            current = current.next;
        }

        employees.sort(
            (a, b) -> a.name.compareToIgnoreCase(b.name)
        );

        System.out.println("\n========== SORTED BY NAME ==========");

        for (Employee emp : employees) {
            displayEmployee(emp);
        }

        System.out.println("Time Complexity: O(N log N)");
    }

    // Add leave request to the queue
    public void addLeaveRequest() {
        int id = readInt("\nEnter employee ID: ");
        Node employee = searchEmployeeById(id);

        if (employee == null) {
            System.out.println("\nEmployee not found.");
            return;
        }

        String reason = readLine("Enter leave reason: ");

        LeaveRequest request = new LeaveRequest(
            id, employee.data.name, reason
        );

        leaveQueue.offer(request);

        actionStack.push(
            "Added leave request for employee ID " + id
        );

        System.out.println("\nLeave request added to queue.");
        System.out.println("Data Structure Used: Queue");
        System.out.println("Complexity: O(1)");
    }

    // Process the oldest leave request (FIFO)
    public void processLeaveRequest() {
        if (leaveQueue.isEmpty()) {
            System.out.println("\nNo pending leave requests.");
            return;
        }

        LeaveRequest request = leaveQueue.poll();

        System.out.println("\nLeave request processed.");
        System.out.println("Employee ID: " + request.employeeId);
        System.out.println("Employee Name: " + request.employeeName);
        System.out.println("Reason: " + request.reason);
        System.out.println("Data Structure Used: Queue");
        System.out.println("Complexity: O(1)");
    }

    // Display pending leave requests without removing them
    public void showLeaveRequests() {
        if (leaveQueue.isEmpty()) {
            System.out.println("\nNo pending leave requests.");
            return;
        }

        System.out.println("\n========== PENDING LEAVE REQUESTS ==========");

        for (LeaveRequest request : leaveQueue) {
            System.out.println("\nEmployee ID: " + request.employeeId);
            System.out.println("Employee Name: " + request.employeeName);
            System.out.println("Reason: " + request.reason);
        }
    }

    // Remove the latest action from history
    public void undoLastAction() {
        if (actionStack.isEmpty()) {
            System.out.println("\nNo action available to undo.");
            return;
        }

        System.out.println("\nLast action: " + actionStack.peek());
        actionStack.pop();

        System.out.println("Action removed from history.");
        System.out.println("Data Structure Used: Stack");
        System.out.println("Complexity: O(1)");
    }

    // Display DSA time complexities
    public void showDSAComplexity() {
        System.out.println("\n============== DSA COMPLEXITY ==============");
        System.out.printf("%-30s %-20s%n", "Operation", "Complexity");
        System.out.printf("%-30s %-20s%n", "Add employee", "O(N)");
        System.out.printf("%-30s %-20s%n", "Search employee", "O(N)");
        System.out.printf("%-30s %-20s%n", "Delete employee", "O(N)");
        System.out.printf("%-30s %-20s%n", "Sort employees", "O(N log N)");
        System.out.printf("%-30s %-20s%n", "Add leave request", "O(1)");
        System.out.printf("%-30s %-20s%n", "Process leave request", "O(1)");
        System.out.printf("%-30s %-20s%n", "Undo action history", "O(1)");

        System.out.println("\nLinked List : Employee records");
        System.out.println("Queue       : Leave requests");
        System.out.println("Stack       : Action history");
        System.out.println("ArrayList   : Temporary storage for sorting");
    }

    // Load sample employees
    public void loadDemoData() {
        Employee[] demoEmployees = {
            new Employee(nextId++, "Aarav Mehta", "Engineering",
                "Senior Developer", 85000, 96, 4.8f),

            new Employee(nextId++, "Meera Nair", "Design",
                "Product Designer", 72000, 91, 4.5f),

            new Employee(nextId++, "Kabir Shah", "Marketing",
                "Growth Lead", 68000, 88, 4.1f),

            new Employee(nextId++, "Ishita Rao", "HR",
                "HR Executive", 56000, 98, 4.6f),

            new Employee(nextId++, "Rohan Batra", "Engineering",
                "QA Analyst", 62000, 93, 3.9f)
        };

        for (Employee emp : demoEmployees) {
            Node newNode = new Node(emp);

            if (head == null) {
                head = newNode;
            } else {
                Node current = head;

                while (current.next != null) {
                    current = current.next;
                }

                current.next = newNode;
            }
        }

        System.out.println("Demo employee data loaded.");
    }

    // Main menu
    public void menu() {
        int choice;

        do {
            System.out.println("\n\n==============================================");
            System.out.println("       EMPLOYEE MANAGEMENT SYSTEM");
            System.out.println("==============================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Sort by Salary");
            System.out.println("7. Sort by Name");
            System.out.println("8. Add Leave Request");
            System.out.println("9. Process Leave Request");
            System.out.println("10. Show Leave Requests");
            System.out.println("11. Undo Last Action");
            System.out.println("12. Show DSA Complexity");
            System.out.println("0. Exit");
            System.out.println("==============================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    sortBySalary();
                    break;
                case 7:
                    sortByName();
                    break;
                case 8:
                    addLeaveRequest();
                    break;
                case 9:
                    processLeaveRequest();
                    break;
                case 10:
                    showLeaveRequests();
                    break;
                case 11:
                    undoLastAction();
                    break;
                case 12:
                    showDSAComplexity();
                    break;
                case 0:
                    System.out.println("\nThank you for using the system.");
                    break;
                default:
                    System.out.println("\nInvalid choice. Try again.");
            }

        } while (choice != 0);
    }
}

public class Main {
    public static void main(String[] args) {
        EmployeeManagementSystem system =
            new EmployeeManagementSystem();

        system.loadDemoData();
        system.menu();
    }
}
