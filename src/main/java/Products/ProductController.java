package Products;

//Shopify Summer 2019 Developer Intern Challenge
//
// George Xu
// Jan 9, 2019

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RestController
@Configuration
@Slf4j


class ProductController {
    private final ProductRepository repository;
    private Map<Long, ShoppingCart> carts = new HashMap<>();

    ProductController(ProductRepository repository){
        this.repository = repository;
    }

    //Hello Shopify! Welcome to my project. This Rest API was built on Spring and compiled with maven. To use this api, I ran it through localhost:8080 <-default port
    // you can use the following URLs:
    // http://localhost:8080/Products
    // http://localhost:8080/Products?available=true
    // http://localhost:8080/Products/{id} (1-3 for the time being)
    // http://localhost:8080/Purchase/{id}
    // http://localhost:8080/makeCart/{id}
    // http://localhost:8080/Cart/AddProduct/{cartId}/{prodId}
    // http://localhost:8080//Cart/Purchase/{cartId}



    //EFFECTS: if parameter with true value is passed, returns all products that are available to purchase
    //         otherwise, parameter is defaulted to false and method returns all products
    //example URL: LocalHost:8080/Products?available=true
    //             LocalHost:8080/Products
    @GetMapping("/Products")
    List<Product> all(@RequestParam(name ="available", defaultValue = "false", required = false) String available){

        List<Product> productList = repository.findAll();
        List<Product> availableProducts = new ArrayList<>();

        if(!"true".equals(available)) {return repository.findAll();}

        for (Product p : productList){
            if (p.isAvailable()){
                availableProducts.add(p);
            }
        }return availableProducts;
    }

    //EFFECTS: If ID is valid, returns a single product with matching ID
    //         else throws a ProductNotFoundException
    //         example URL: LocalHost:8080/Products/1
    @GetMapping("/Products/{id}")
    Product one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //EFFECTS: If ID is valid, purchases a single product with matching ID and returns true
    //         else throws a ProductNotFoundException and returns false
    //         example URL: Localhost:8080/Purchase/1
    @GetMapping("/Purchase/{id}")
    boolean purchaseOne(@PathVariable Long id) {
        Product p = repository.findById(id)
               .orElseThrow(() -> new ProductNotFoundException(id));
               boolean isSuccessful = p.purchase();
               repository.deleteById(id);
               repository.save(p);
        return isSuccessful;
    }

    //Creates a new shopping cart with unique id
    @GetMapping("/makeCart/{id}")
    Long makeCart(@PathVariable Long id){
        ShoppingCart cart = new ShoppingCart(id);
        carts.put(cart.getId(), cart);
        log.info("creating cart " + cart);
        return cart.getId();
    }

    //EFFECTS: throws ProductNotFoundException if specific product id not found
    //         throws CartNotFoundException if specific cart id is not found
    //         adds specific product to specific cart if no exceptions are thrown
    @GetMapping("/Cart/AddProduct/{cartId}/{prodId}")
    void addProductToCart(@PathVariable Long cartId, @PathVariable Long prodId){
        Product p = repository.findById(prodId)
                .orElseThrow(() -> new ProductNotFoundException(prodId));
        carts.get(cartId).addItem(p);
        log.info("shopping cart id is : " +carts.get(cartId));
        log.info("adding product "+p.getTitle());

    }
    //EFFECTS: purchase all items in cart and return total cost of items
    @GetMapping("/Cart/Purchase/{cartId}")
    double purchaseCartItems(@PathVariable Long cartId){
        log.info("cart to be purchased "+ carts);

            double totalCost =0;

            for (Map.Entry<Product, Integer> cartItem : carts.get(cartId).getCartItems().entrySet()){
                log.info("product id " + cartItem.getKey().getId() + " is about to be purchased");
                Long prodId = cartItem.getKey().getId();
                Product p= repository.findById(prodId)
                        .orElseThrow(() -> new ProductNotFoundException(prodId));
                p.purchase();
                repository.deleteById(prodId);
                repository.save(p);

                log.info("purchasing item from cart " + cartItem.getKey().getTitle());
                totalCost += cartItem.getKey().getPrice();
                log.info("cost so far: " + totalCost);
            }
            carts.get(cartId).clear();
        carts.remove(cartId);
        log.info(repository + "");
        log.info("available carts: " +carts);
        log.info("purchased items from cart!");
        return totalCost;

    }



}