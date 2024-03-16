package com.example.laundrypersonnelmis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class services_activity extends AppCompatActivity {

    private static final String TAG = "services_activity";

    private CardView[] cardViews;
    private Button nextButton;
    private String userPhone;
    private String collectionDateTime;
    private String deliveryDateTime;
    private String frequency;
    private String specialInstructionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

         //Get the user's phone number from the intent
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("phone");
        collectionDateTime = intent.getStringExtra("collectionDateTime");
        deliveryDateTime = intent.getStringExtra("deliveryDateTime");
        frequency = intent.getStringExtra("frequency");
        specialInstructionsText=intent.getStringExtra("specialInstructionsText");

        // Initialize CardViews
        cardViews = new CardView[]{
                findViewById(R.id.firstCardView),
                findViewById(R.id.secondCardView),
                findViewById(R.id.thirdCardView),
                findViewById(R.id.fourthCardView),
                findViewById(R.id.fifthCardView)
        };

        nextButton = findViewById(R.id.nextButton);
        StringBuilder selectedItemsBuilder = new StringBuilder();

        // Set OnClickListener for each CardView
        for (final CardView cardView : cardViews) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleCardViewColor(cardView);
                    toggleNextButton(); // Update the Next button state on each card click
                }
            });
        }

        // Set OnClickListener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if any card is selected
                for (CardView cardView : cardViews) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.cardbg)) {
                        Object tag = cardView.getTag();
                        if (tag != null) {
                            String selectedCard = tag.toString();
                            selectedItemsBuilder.append(selectedCard).append("\n");
                        }
                    }
                }
                if (isAnyCardSelected()) {
                    // Navigate to Service_Order_DetailsActivity
                    Intent intent = new Intent(services_activity.this, Service_Order_DetailsActivity.class);
                    intent.putExtra("phone", userPhone);
                    intent.putExtra("Orders", selectedItemsBuilder.toString()); // Pass the selectedItems data
                    intent.putExtra("collectionDateTime", collectionDateTime);
                    intent.putExtra("deliveryDateTime", deliveryDateTime);
                    intent.putExtra("frequency",frequency);
                    intent.putExtra("specialInstructionsText",specialInstructionsText);
                    startActivity(intent);

                } else {
                    Toast.makeText(services_activity.this, "Please select at least one service", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Toggle Next button based on initial card selections
        toggleNextButton();
    }

    // Method to toggle the color of the CardView
    private void toggleCardViewColor(CardView cardView) {
        // Toggle the card background color
        if (cardView.getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.cardbg)) {
            cardView.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.cardbg));
        }
    }

    // Method to toggle the state of the Next button
    private void toggleNextButton() {
        boolean anyCardSelected = isAnyCardSelected();
        nextButton.setEnabled(anyCardSelected);
    }

    // Method to check if any card is selected
    private boolean isAnyCardSelected() {
        for (CardView cardView : cardViews) {
            if (cardView.getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.cardbg)) {
                return true;
            }
        }
        return false;
    }
}
