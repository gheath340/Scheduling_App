package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {
    /**
     *
     */
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     *
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     *
     * @param customer
     */
    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }


}
