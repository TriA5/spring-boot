package com.example.buoi5.controller;

import com.example.buoi5.model.Cart;
import com.example.buoi5.model.User;
import com.example.buoi5.repository.UserRepository;
import com.example.buoi5.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    private User getUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = getUser(userDetails);
        Cart cart = cartService.getCartByUser(user.getId());
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart/view";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        User user = getUser(userDetails);
        cartService.addItem(user.getId(), productId, quantity);
        redirectAttributes.addFlashAttribute("cartSuccess", "Đã thêm vào giỏ hàng!");
        return "redirect:/products";
    }

    @PostMapping("/remove/{itemId}")
    public String removeItem(@PathVariable Long itemId,
            @AuthenticationPrincipal UserDetails userDetails) {
        cartService.removeItem(itemId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getUser(userDetails);
        cartService.clearCart(user.getId());
        return "redirect:/cart";
    }
}
