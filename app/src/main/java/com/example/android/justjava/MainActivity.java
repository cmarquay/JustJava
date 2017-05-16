package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, getString(R.string.too_many), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, getString(R.string.too_few), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameDescriptionView = (EditText) findViewById(R.id.name_description_view);
        String name = nameDescriptionView.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(getString(R.string.mail))); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(numberOfCoffees));
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;
        if (addWhippedCream) {
            price += 1;
        }
        if (addChocolate) {
            price += 2;
        }
        return price * quantity;
    }

    /**
     * Write the summary of the order based on the current quantity.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param name            of the customer
     * @return the summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String summary = getString(R.string.name_summary, name);
        summary += "\n" + getString(R.string.add_whipped_cream_summary, addWhippedCream);
        summary += "\n" + getString(R.string.add_chocolate_summary, addChocolate);
        summary += "\n" + getString(R.string.quantity_summary, quantity);
        summary += "\n" + getString(R.string.total_summary, price);
        summary += "\n" + getString(R.string.thank_you);
        return summary;
    }
}