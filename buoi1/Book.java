import java.util.Scanner;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;

    // Constructor đầy đủ tham số
    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Constructor mặc định
    public Book() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Phương thức nhập thông tin sách
    public void input() {
        Scanner x = new Scanner(System.in);
        System.out.print("Nhập mã sách: ");
        this.id = Integer.parseInt(x.nextLine());
        System.out.print("Nhập tên sách: ");
        this.title = x.nextLine();
        System.out.print("Nhập tác giả: ");
        this.author = x.nextLine();
        System.out.print("Nhập đơn giá: ");
        this.price = x.nextDouble();
    }

    // Phương thức xuất thông tin sách
    public void output() {
        String msg = String.format("id=%s, title=%s, author=%s, price=%.0f", id, title, author, price);
        System.out.println(msg);
    }
}
