package Products;

class CartNotFoundException extends RuntimeException {
    CartNotFoundException(Long id)
    {
        super("Could not find Cart " + id);
    }
}
