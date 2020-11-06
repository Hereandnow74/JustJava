package com.michaelklepikov174.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // User add his name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //Only Email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "Klepikovmichael174@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * @param "quantity" is the number of cups of coffee ordered
     * @param addWhippedCream is whenever user want whipped cream topping to his coffee
     * @param addChocolate is whenever user want chocolate topping to his coffee
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price for a cup of coffee
        int basePrice = 5;

        //Add 1$ if use wants whipped cream topping
        if (addWhippedCream == true) {
            basePrice = basePrice + 1;
        }
        //Add 1$ if use wants chocolate topping
        if (addChocolate == true) {
            basePrice = basePrice + 2;
        }

        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }
    /**
     * Create summary of our order
     *
     * @param price of order
     * @param addWhippedCream is whenever user want whipped cream topping to his coffee
     * @param addChocolate is whenever user want chocolate topping to his coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

    /**
     * This method is called when the "+" button is clicked.
     */
    public void increment (View view) {
        if (quantity == 100) {
            //Show toast message
            Toast.makeText(this, "You cannot have more than 100 cups of coffees", Toast.LENGTH_SHORT).show();
            //Exit method early because there is nothing left to do
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the "-" button is clicked.
     */
    public void decrement (View view) {
        if (quantity == 1) {
            //Show toast message
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            //Exit method early because there is nothing left to do
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}