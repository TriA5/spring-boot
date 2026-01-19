public class Book {
    private int id;
    private String title;
    private String author;
    private double price;

    // Constructor mặc định
    public Book() {
    }

    // Constructor đầy đủ tham số
    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Phương thức nhập thông tin sách
    public void input() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Nhap ma sach: ");
        this.id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhap ten sach: ");
        this.title = scanner.nextLine();
        System.out.print("Nhap tac gia: ");
        this.author = scanner.nextLine();
        System.out.print("Nhap don gia: ");
        this.price = Double.parseDouble(scanner.nextLine());
    }

    // Phương thức xuất thông tin sách
    public void output() {
        System.out.printf("%-10d %-30s %-20s %,.0f VND%n", id, title, author, price);
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', price=%.0f}", id, title, author, price);
    }
}
