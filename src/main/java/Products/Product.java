
//Task: Build the barebones of an online marketplace.
//
//        To do this, build a server side web api that can be used to fetch products either one at a time or all at once.
//        Every product should have a title, price, and inventory_count.
//
//        Querying for all products should support passing an argument to only return products with available inventory.
//
//        Products should be able to be "purchased" which should reduce the inventory by 1. Products with no inventory cannot be purchased.


package Products;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Product{

    private @Id Long id;
    private  String title;
    private double price;
    private int inventory_count;
    private boolean available;

    Product(){

    }
    //EFFECTS: constructs a Product with a unique id key, a title (name), its price and inventory count, as well as its availability to purchase
    Product(Long id, String title, double price, int invCount){
        this.id = id;
        this.title = title;
        this.price = price;
        this.inventory_count = invCount;
        this.available = this.inventory_count >0;

    }
    //EFFECTS: returns product's price
    public double getPrice(){
        return this.price;
    }
    //EFFECTS: returns product's id
    public Long getId(){
        return this.id;
    }
    //EFFECTS returns product's title
    public String getTitle(){
        return this.title;
    }

    //EFFECTS: returns if product is available to purchase
    public boolean isAvailable(){
        return this.available;
    }
    //MODIFIES: this
    //EFFECTS: decreases inventory by 1 and returns true if there is inventory available for purchase,
    //         otherwise returns false
    public boolean purchase(){

        if(this.available){
            this.inventory_count -= 1;
            System.out.println("purchase of  successful!");
            return true;

        }else {
            System.out.println("purchase of  unsuccessful, out of stock!");
            return false;
        }

    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Product)) return false;
        Product product = (Product) object;
        return this.id ==product.getId();
    }

    @Override
    public int hashCode() {

        return Math.toIntExact(this.id);
    }
}