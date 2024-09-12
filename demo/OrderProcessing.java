import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderProcessing {
    public static void main(String[] args){
        List<Order> orders = Arrays.asList(
                new Order("1", 250.0, "COMPLETED"),
                new Order("2", 150.0, "PENDING"),
                new Order("3", 450.0, "COMPLETED"),
                new Order("4", 75.0, "PENDING"),
                new Order("5", 300.0, "COMPLETED")
        );

        List<Order> completedorder=orders.stream()
                .filter(order -> "completed".equals(order.getStatus()))
                .collect(Collectors.toList());

        double totalAmount=completedorder.stream()
                .mapToDouble(Order :: getAmount)
                .sum();

        double highestAmount=completedorder.stream()
                .mapToDouble(Order :: getAmount)
                .max()
                .orElse(0);
    }
}
