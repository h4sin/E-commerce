package Ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Ecommerce.Common.ApiResponse;
import Ecommerce.DTO.Cart.AddToCartDTO;
import Ecommerce.DTO.Cart.CartDTO;
import Ecommerce.Model.User;
import Ecommerce.Service.AuthenticationService;
import Ecommerce.Service.CartService;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDTO addToCartDto,
			@RequestParam("token") String token) {
		authenticationService.authenticate(token);
		User user = authenticationService.getUser(token);
		cartService.addToCart(addToCartDto, user);
		return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<CartDTO> getCartItems(@RequestParam("token") String token) {
		authenticationService.authenticate(token);
		User user = authenticationService.getUser(token);
		CartDTO cartDto = cartService.listCartItems(user);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
			@RequestParam("token") String token) {
		authenticationService.authenticate(token);
		User user = authenticationService.getUser(token);
		cartService.deleteCartItem(itemId, user);
		return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
	}

}
