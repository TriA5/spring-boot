package com.example.buoi5.controller;

import com.example.buoi5.model.Order;
import com.example.buoi5.model.User;
import com.example.buoi5.repository.UserRepository;
import com.example.buoi5.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    private User getUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String shippingAddress,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        User user = getUser(userDetails);
        try {
            Order order = orderService.placeOrder(user.getId(), shippingAddress);
            redirectAttributes.addFlashAttribute("orderId", order.getId());
            return "redirect:/orders/success";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/success")
    public String success() {
        return "order/success";
    }

    @GetMapping("/my-orders")
    public String myOrders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = getUser(userDetails);
        List<Order> orders = orderService.getOrdersByUser(user.getId());
        model.addAttribute("orders", orders);
        return "order/my-orders";
    }
}
