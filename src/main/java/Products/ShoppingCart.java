package Products;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ElementCollection;

import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShoppingCart {
    @ElementCollection
    private Map<Product, Integer> cartItems = new HashMap<>();
    private @Id Long id;

    public ShoppingCart(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }
    public void clear(){
        this.cartItems.clear();
    }
    //EFFECTS: purchases each item in cart and returns total cost
//    public double purchaseCart(){
//        double totalCost =0;
//
//        for (Map.Entry<Product, Integer> cartItem : cartItems.entrySet()){
//
//            repository.findById(cartItem.getKey().getId()).purchase();
//
//            log.info("purchasing item from cart " + cartItem.getKey().getTitle());
//            totalCost += cartItem.getKey().getPrice();
//            log.info("cost so far: " + totalCost);
//        }
//        cartItems.clear();
//        return totalCost;
//
//    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("shopping cart id is "+ this.id + "and contains products: ");
        for(Map.Entry<Product, Integer> cartItem : cartItems.entrySet()){
            sb.append(cartItem.getKey().getId() + ", ");
        }return sb.toString();
    }



    //MODIFIES: this
    //EFFECTS: if cartItems does not contain product, add product with quantity 1
    //         if cartItems contains product already, increment quantity by 1
    public void addItem(Product p){
        if (!cartItems.containsKey(p)){
            cartItems.put(p, 1);

        }else {
            cartItems.put(p, cartItems.get(p.getId())+1);
        }
    }

}