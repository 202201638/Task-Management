interface Shippable {
    String getName();
    double getWeight();
}

abstract class Product {
    private String name;
    private double price;
    private int quantity;
    private boolean expired;

    public Product(String name, double price, int quantity, boolean expired) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expired = expired;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isExpired() {
        return expired;
    }

    public void decreaseQuantity(int q) {
        quantity -= q;
    }

    public boolean isShippable() {
        return this instanceof Shippable;
    }
}

class Cheese extends Product implements Shippable {
    private double weight;

    public Cheese(String name, double price, int quantity, boolean expired, double weight) {
        super(name, price, quantity, expired);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}

class Biscuits extends Product implements Shippable {
    private double weight;

    public Biscuits(String name, double price, int quantity, boolean expired, double weight) {
        super(name, price, quantity, expired);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}

class TV extends Product implements Shippable {
    private double weight;

    public TV(String name, double price, int quantity, double weight) {
        super(name, price, quantity, false);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}

class Mobile extends Product {
    public Mobile(String name, double price, int quantity) {
        super(name, price, quantity, false);
    }
}

class ScratchCard extends Product {
    public ScratchCard(String name, double price, int quantity) {
        super(name, price, quantity, false);
    }
}

class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deduct(double amount) {
        balance -= amount;
    }
}

class CartItem {
    Product product;
    int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Quantity exceeds available stock.");
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

class ShippingService {
    public static void ship(List<Shippable> items) {
        double totalWeight = 0;
        System.out.println("** Shipment notice **");
        for (Shippable s : items) {
            double w = s.getWeight();
            System.out.printf("%sx %-10s %.0fg\n", 1, s.getName(), w * 1000);
            totalWeight += w;
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight);
    }
}

public class Main {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        double subtotal = 0;
        List<Shippable> toShip = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            if (ci.product.isExpired()) {
                throw new IllegalStateException(ci.product.getName() + " is expired.");
            }
            if (ci.quantity > ci.product.getQuantity()) {
                throw new IllegalStateException("Not enough stock for " + ci.product.getName());
            }
            subtotal += ci.product.getPrice() * ci.quantity;
            if (ci.product.isShippable()) {
                for (int i = 0; i < ci.quantity; i++) {
                    toShip.add((Shippable) ci.product);
                }
            }
        }

        double shipping = toShip.isEmpty() ? 0 : 30;
        double total = subtotal + shipping;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance.");
        }

        for (CartItem ci : cart.getItems()) {
            ci.product.decreaseQuantity(ci.quantity);
        }

        customer.deduct(total);

        if (!toShip.isEmpty()) {
            ShippingService.ship(toShip);
        }

        System.out.println("** Checkout receipt **");
        for (CartItem ci : cart.getItems()) {
            System.out.printf("%sx %-10s %.0f\n", ci.quantity, ci.product.getName(), ci.product.getPrice() * ci.quantity);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal         %.0f\n", subtotal);
        System.out.printf("Shipping         %.0f\n", shipping);
        System.out.printf("Amount           %.0f\n", total);
        System.out.printf("Remaining Balance %.0f\n", customer.getBalance());
    }

    public static void main(String[] args) {
        Cheese cheese = new Cheese("Cheese", 100, 5, false, 0.2);
        Biscuits biscuits = new Biscuits("Biscuits", 150, 2, false, 0.7);
        TV tv = new TV("TV", 5000, 2, 15);
        ScratchCard scratchCard = new ScratchCard("ScratchCard", 50, 10);
        Mobile mobile = new Mobile("Mobile", 3000, 5);

        Customer customer = new Customer("John", 5000);

        Cart cart = new Cart();
        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        

        checkout(customer, cart);
    }
}