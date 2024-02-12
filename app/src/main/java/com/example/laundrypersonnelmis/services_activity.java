package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.cardview.widget.CardView;

public class services_activity extends AppCompatActivity {

    CardView[] cardViews;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // Initialize CardViews
        cardViews = new CardView[]{
                findViewById(R.id.firstCardView),
                findViewById(R.id.secondCardView),
                findViewById(R.id.thirdCardView),
                findViewById(R.id.fourthCardView),
                findViewById(R.id.fifthCardView)
        };

        nextButton = findViewById(R.id.nextButton);

        // Set OnClickListener for each CardView
        for (CardView cardView : cardViews) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleCardViewColor(cardView);
                    toggleNextButton();
                }
            });
        }
    }

    // Method to toggle the color of the CardView
    private void toggleCardViewColor(CardView cardView) {
        if (cardView.getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.cardbg)) {
            cardView.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.cardbg));
        }
    }

    // Method to toggle the state of the Next button
    private void toggleNextButton() {
        boolean anyCardSelected = false;
        for (CardView cardView : cardViews) {
            if (cardView.getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.cardbg)) {
                anyCardSelected = true;
                break;
            }
        }
        nextButton.setEnabled(anyCardSelected);
    }
}
