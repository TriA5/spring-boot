import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookManager {
    public static void main(String[] args) {
        Scanner x = new Scanner(System.in);
        ArrayList<Book> listBook = new ArrayList<>();
        String msg = "===== MENU QUAN LY SACH =====\n" +
                "1. Them 1 cuon sach\n" +
                "2. Xoa 1 cuon sach\n" +
                "3. Thay doi cuon sach\n" +
                "4. Xuat thong tin tat ca cac cuon sach\n" +
                "5. Tim cuon sach co tua de chua chu \"Lap trinh\"\n" +
                "6. Lay sach: Nhap vao 1 so K va gia sach P\n" +
                "7. Nhap vao 1 danh sach cac tac gia\n" +
                "0. Thoat\n" +
                "=============================\n" +
                "Chon chuc nang: ";

        int chon = 0;
        do {
            System.out.print(msg);
            chon = x.nextInt();
            x.nextLine(); // Đọc bỏ dòng thừa

            switch (chon) {
                case 1 -> {
                    // Thêm 1 cuốn sách
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                    System.out.println("Da them sach thanh cong!\n");
                }

                case 2 -> {
                    // Xoa 1 cuon sach
                    System.out.print("Nhap ma sach can xoa: ");
                    int deleteId = x.nextInt();

                    // Sử dụng Stream để lọc và xóa
                    List<Book> filteredList = listBook.stream()
                            .filter(b -> b.getId() != deleteId)
                            .collect(Collectors.toList());

                    if (listBook.size() > filteredList.size()) {
                        listBook.clear();
                        listBook.addAll(filteredList);
                        System.out.println("Da xoa sach thanh cong!\n");
                    } else {
                        System.out.println("Khong tim thay sach co ma " + deleteId + "\n");
                    }
                }

                case 3 -> {
                    // Thay doi cuon sach
                    System.out.print("Nhap ma sach can thay doi: ");
                    int updateId = x.nextInt();
                    x.nextLine(); // Đọc bỏ dòng thừa

                    boolean found = false;
                    for (Book book : listBook) {
                        if (book.getId() == updateId) {
                            System.out.println("Nhap thong tin moi:");
                            System.out.print("Nhap ten sach: ");
                            book.setTitle(x.nextLine());
                            System.out.print("Nhap tac gia: ");
                            book.setAuthor(x.nextLine());
                            System.out.print("Nhap don gia: ");
                            book.setPrice(x.nextDouble());
                            System.out.println("Da cap nhat sach thanh cong!\n");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Khong tim thay sach co ma " + updateId + "\n");
                    }
                }

                case 4 -> {
                    // Xuat thong tin tat ca cac cuon sach
                    if (listBook.isEmpty()) {
                        System.out.println("Danh sach sach trong!\n");
                    } else {
                        System.out.println("\n===== DANH SACH TAT CA SACH =====");
                        listBook.forEach(p -> p.output());
                        System.out.println();
                    }
                }

                case 5 -> {
                    // Tim cuon sach co tua de chua chu "Lap trinh" (khong phan biet hoa thuong)
                    System.out.println("\n===== SACH CO TUA DE CHUA 'LAP TRINH' =====");
                    List<Book> list5 = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lap trinh"))
                            .toList();

                    if (list5.isEmpty()) {
                        System.out.println("Khong tim thay sach nao chua 'Lap trinh'\n");
                    } else {
                        list5.forEach(Book::output);
                        System.out.println();
                    }
                }

                case 6 -> {
                    // Lay sach: Nhap vao 1 so K va gia sach P
                    System.out.print("Nhap so luong sach K muon lay: ");
                    int k = x.nextInt();
                    System.out.print("Nhap gia sach P: ");
                    double p = x.nextDouble();

                    System.out.println("\n===== " + k + " SACH DAU TIEN CO GIA <= " + p + " =====");
                    List<Book> list6 = listBook.stream()
                            .filter(book -> book.getPrice() <= p)
                            .limit(k)
                            .toList();

                    if (list6.isEmpty()) {
                        System.out.println("Khong tim thay sach nao thoa man dieu kien\n");
                    } else {
                        list6.forEach(Book::output);
                        System.out.println();
                    }
                }

                case 7 -> {
                    // Nhap vao 1 danh sach cac tac gia
                    System.out.print("Nhap so luong tac gia: ");
                    int n = x.nextInt();
                    x.nextLine(); // Doc bo dong thua

                    List<String> authors = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Nhap ten tac gia thu " + (i + 1) + ": ");
                        authors.add(x.nextLine());
                    }

                    System.out.println("\n===== SACH CUA CAC TAC GIA =====");
                    List<Book> list7 = listBook.stream()
                            .filter(book -> authors.contains(book.getAuthor()))
                            .toList();

                    if (list7.isEmpty()) {
                        System.out.println("Khong tim thay sach nao cua cac tac gia nay\n");
                    } else {
                        list7.forEach(Book::output);
                        System.out.println();
                    }
                }

                case 0 -> {
                    System.out.println("Thoat chuong trinh. Tam biet!");
                }

                default -> {
                    System.out.println("Lua chon khong hop le. Vui long chon lai!\n");
                }
            }
        } while (chon != 0);

        x.close();
    }
}
