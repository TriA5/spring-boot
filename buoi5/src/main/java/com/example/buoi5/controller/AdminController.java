package com.example.buoi5.controller;

import com.example.buoi5.model.Order;
import com.example.buoi5.model.OrderStatus;
import com.example.buoi5.model.User;
import com.example.buoi5.repository.UserRepository;
import com.example.buoi5.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String dashboard(Model model) {
        List<Order> orders = orderService.getAllOrders();
        List<User> users = userRepository.findAll();
        long pendingOrders = orders.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count();
        double totalRevenue = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotalAmount).sum();
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        return "admin/dashboard";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id,
            @RequestParam OrderStatus status,
            RedirectAttributes redirectAttributes) {
        orderService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Đã cập nhật trạng thái đơn hàng!");
        return "redirect:/admin/orders";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }
}
