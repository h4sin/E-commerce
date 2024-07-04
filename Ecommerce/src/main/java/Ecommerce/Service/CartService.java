package Ecommerce.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ecommerce.DTO.Cart.AddToCartDTO;
import Ecommerce.DTO.Cart.CartDTO;
import Ecommerce.DTO.Cart.CartItemDTO;
import Ecommerce.Exeptions.CustomException;
import Ecommerce.Model.Cart;
import Ecommerce.Model.Product;
import Ecommerce.Model.User;
import Ecommerce.Repository.CartRepository;

@Service
public class CartService {
	 	@Autowired
	    ProductService productService;

	    @Autowired
	    CartRepository cartRepository;

	    public void addToCart(AddToCartDTO addToCartDto, User user) {
	        Product product = productService.findById(addToCartDto.getProductId());
	        Cart cart = new Cart();
	        cart.setProduct(product);
	        cart.setUser(user);
	        cart.setQuantity(addToCartDto.getQuantity());
	        cart.setCreatedDate(new Date());
	        cartRepository.save(cart);

	    }

	    public CartDTO listCartItems(User user) {
	        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

	        List<CartItemDTO> cartItems = new ArrayList<>();
	        double totalCost = 0;
	        for (Cart cart: cartList) {
	            CartItemDTO cartItemDto = new CartItemDTO(cart);
	            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
	            cartItems.add(cartItemDto);
	        }

	        CartDTO cartDto = new CartDTO();
	        cartDto.setTotalCost(totalCost);
	        cartDto.setCartItems(cartItems);
	        return  cartDto;
	    }

	    public void deleteCartItem(Integer cartItemId, User user) {
	        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

	        if (optionalCart.isEmpty()) {
	            throw new CustomException("cart item id is invalid: " + cartItemId);
	        }

	        Cart cart = optionalCart.get();

	        if (cart.getUser() != user) {
	            throw  new CustomException("cart item does not belong to user: " +cartItemId);
	        }

	        cartRepository.delete(cart);


	    }
}
