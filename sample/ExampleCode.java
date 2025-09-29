import java.util.*;

/**
 * Example Java class with various code quality issues for testing the AI reviewer
 */
public class ExampleCode {
    
    private String name;
    private int age;
    private List<String> items;
    
    // Constructor without validation
    public ExampleCode(String name, int age) {
        this.name = name;
        this.age = age;
        this.items = new ArrayList<>();
    }
    
    // Method with potential null pointer exception
    public void addItem(String item) {
        items.add(item); // No null check
    }
    
    // Method with inefficient string concatenation
    public String getInfo() {
        String result = "";
        for (int i = 0; i < items.size(); i++) {
            result += items.get(i) + ", "; // Inefficient string concatenation
        }
        return "Name: " + name + ", Age: " + age + ", Items: " + result;
    }
    
    // Method with magic numbers
    public boolean isAdult() {
        return age >= 18; // Magic number
    }
    
    // Method with potential division by zero
    public double calculateAverage() {
        int sum = 0;
        for (String item : items) {
            sum += item.length();
        }
        return sum / items.size(); // Potential division by zero
    }
    
    // Method with unused parameter
    public void processItems(String prefix, boolean sort) {
        // 'sort' parameter is unused
        for (String item : items) {
            System.out.println(prefix + item);
        }
    }
    
    // Method with hardcoded values
    public void saveToFile() {
        // Hardcoded file path
        String filename = "/tmp/data.txt";
        // Missing exception handling
        // File operations would go here
    }
    
    // Getter without null safety
    public String getName() {
        return name; // Could return null
    }
    
    // Setter without validation
    public void setAge(int age) {
        this.age = age; // No validation for negative values
    }
    
    // Method with long parameter list
    public void complexMethod(String a, String b, String c, String d, String e, String f) {
        // Too many parameters
    }
    
    // Method with deep nesting
    public void nestedMethod() {
        if (items != null) {
            if (items.size() > 0) {
                for (String item : items) {
                    if (item != null) {
                        if (item.length() > 5) {
                            System.out.println("Long item: " + item);
                        }
                    }
                }
            }
        }
    }
}
