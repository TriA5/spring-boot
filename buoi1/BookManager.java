import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> listBook;
    private Scanner scanner;

    public BookManager() {
        listBook = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // 1. Thêm 1 cuốn sách
    public void addBook() {
        System.out.println("\n=== THEM CUON SACH MOI ===");
        Book newBook = new Book();
        newBook.input();
        listBook.add(newBook);
        System.out.println("Them sach thanh cong!");
    }

    // 2. Xóa 1 cuốn sách
    public void deleteBook() {
        System.out.println("\n=== XOA CUON SACH ===");
        System.out.print("Nhap ma sach can xoa: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        boolean removed = listBook.removeIf(book -> book.getId() == bookId);

        if (removed) {
            System.out.println("Xoa sach thanh cong!");
        } else {
            System.out.println("Khong tim thay sach voi ma: " + bookId);
        }
    }

    // 3. Thay đổi cuốn sách
    public void updateBook() {
        System.out.println("\n=== THAY DOI THONG TIN SACH ===");
        System.out.print("Nhap ma sach can thay doi: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        Book bookToUpdate = listBook.stream()
                .filter(book -> book.getId() == bookId)
                .findFirst()
                .orElse(null);

        if (bookToUpdate != null) {
            System.out.println("Thong tin hien tai:");
            bookToUpdate.output();
            System.out.println("\nNhap thong tin moi:");
            bookToUpdate.input();
            System.out.println("Cap nhat thanh cong!");
        } else {
            System.out.println("Khong tim thay sach voi ma: " + bookId);
        }
    }

    // 4. Xuất thông tin tất cả các cuốn sách
    public void displayAllBooks() {
        System.out.println("\n=== DANH SACH TAT CA CAC CUON SACH ===");
        if (listBook.isEmpty()) {
            System.out.println("Danh sach sach rong!");
            return;
        }

        System.out.printf("%-10s %-30s %-20s %-15s%n", "Ma Sach", "Ten Sach", "Tac Gia", "Don Gia");
        System.out.println("=".repeat(80));
        listBook.forEach(Book::output);
    }

    // 5. Tìm cuốn sách có tựa đề chứa chữ "Lập trình" và không phân biệt hoa thường
    public void findBooksByTitle() {
        System.out.println("\n=== TIM SACH CO TUA DE CHUA 'LAP TRINH' ===");

        List<Book> foundBooks = listBook.stream()
                .filter(book -> book.getTitle().toLowerCase().contains("lap trinh"))
                .collect(Collectors.toList());

        if (foundBooks.isEmpty()) {
            System.out.println("Khong tim thay sach nao co tua de chua 'lap trinh'");
        } else {
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Ma Sach", "Ten Sach", "Tac Gia", "Don Gia");
            System.out.println("=".repeat(80));
            foundBooks.forEach(Book::output);
        }
    }

    // 6. Lấy sách: Nhập vào 1 số K và giá sách P mong muốn tìm kiếm
    // Hãy lấy tối đa K cuốn sách đều thỏa mãn có giá sách <= P
    public void getBooksByPriceLimit() {
        System.out.println("\n=== LAY SACH THEO GIA ===");
        System.out.print("Nhap so luong sach toi da (K): ");
        int k = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhap gia sach mong muon (P): ");
        double p = Double.parseDouble(scanner.nextLine());

        List<Book> filteredBooks = listBook.stream()
                .filter(book -> book.getPrice() <= p)
                .limit(k)
                .collect(Collectors.toList());

        if (filteredBooks.isEmpty()) {
            System.out.println("Khong tim thay sach nao co gia <= " + p);
        } else {
            System.out.println("Danh sach " + filteredBooks.size() + " cuon sach co gia <= " + p + ":");
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Ma Sach", "Ten Sach", "Tac Gia", "Don Gia");
            System.out.println("=".repeat(80));
            filteredBooks.forEach(Book::output);
        }
    }

    // 7. Nhập vào 1 danh sách các tác giả từ bàn phím
    // Hãy cho biết tất cả cuốn sách của những tác giả này?
    public void findBooksByAuthors() {
        System.out.println("\n=== TIM SACH THEO TAC GIA ===");
        System.out.print("Nhap so luong tac gia: ");
        int n = Integer.parseInt(scanner.nextLine());

        List<String> authors = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Nhap ten tac gia thu " + (i + 1) + ": ");
            authors.add(scanner.nextLine().toLowerCase());
        }

        List<Book> foundBooks = listBook.stream()
                .filter(book -> authors.contains(book.getAuthor().toLowerCase()))
                .collect(Collectors.toList());

        if (foundBooks.isEmpty()) {
            System.out.println("Khong tim thay sach nao cua cac tac gia da nhap");
        } else {
            System.out.println("Danh sach sach cua cac tac gia da nhap:");
            System.out.printf("%-10s %-30s %-20s %-15s%n", "Ma Sach", "Ten Sach", "Tac Gia", "Don Gia");
            System.out.println("=".repeat(80));
            foundBooks.forEach(Book::output);
        }
    }

    // Hiển thị menu
    public void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         CHUONG TRINH QUAN LY SACH");
        System.out.println("=".repeat(50));
        System.out.println("1. Them 1 cuon sach");
        System.out.println("2. Xoa 1 cuon sach");
        System.out.println("3. Thay doi cuon sach");
        System.out.println("4. Xuat thong tin tat ca cac cuon sach");
        System.out.println("5. Tim cuon sach co tua de chua 'Lap trinh'");
        System.out.println("6. Lay sach theo gia");
        System.out.println("7. Tim sach theo tac gia");
        System.out.println("0. Thoat");
        System.out.println("=".repeat(50));
    }

    // Chạy chương trình
    public void run() {
        int choice;
        do {
            displayMenu();
            System.out.print("Chon chuc nang: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    displayAllBooks();
                    break;
                case 5:
                    findBooksByTitle();
                    break;
                case 6:
                    getBooksByPriceLimit();
                    break;
                case 7:
                    findBooksByAuthors();
                    break;
                case 0:
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);

        scanner.close();
    }

    public static void main(String[] args) {
        BookManager manager = new BookManager();
        manager.run();
    }
}
