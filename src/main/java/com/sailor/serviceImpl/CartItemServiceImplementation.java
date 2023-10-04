package com.sailor.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sailor.entity.Cart;
import com.sailor.entity.CartItem;
import com.sailor.entity.Product;
import com.sailor.entity.User;
import com.sailor.exception.CartItemException;
import com.sailor.exception.UserException;
import com.sailor.repository.CartItemRepository;
import com.sailor.service.CartItemService;
import com.sailor.service.CartService;
import com.sailor.service.UserService;

@Service
public class CartItemServiceImplementation implements CartItemService {

	private UserService userService;
	private CartItemRepository cartItemRepo;
	private CartService cartService;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountPrice(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());
		CartItem savedCartItem = cartItemRepo.save(cartItem);

		return savedCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartitemByld(id);
		User user = userService.findUserById(item.getUserId());
		if (user.getId().equals(userId)) {

			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			cartItem.setDiscountPrice(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());
		}
		return cartItemRepo.save(item);
	}

	@Override
	public CartItem isCartitemExist(Cart cart, Product product, String size, Long userid) {
		return cartItemRepo.isCartItemExist(cart, product, size, userid);
	}

	@Override
	public void removeCartitem(Long userid, Long cartitemid) throws CartItemException, UserException {
    CartItem cartItem = findCartitemByld(cartitemid);
    
User user = userService.findUserById(cartItem.getUserId());    
User reqUser = userService.findUserById(userid);

if(user.getId().equals(reqUser.getId())) {
	cartItemRepo.deleteById(cartitemid);
	
}
else {
	throw new UserException("You cant't delete");
}

	}

	@Override
	public CartItem findCartitemByld(Long cartitemid) throws CartItemException {
  Optional<CartItem> cartItem = cartItemRepo.findById(cartitemid);		
  if(cartItem.isPresent()) {
	  return cartItem.get();
  }
  throw new CartItemException("cartItem not found with id :"+ cartitemid);
		
	}

}
