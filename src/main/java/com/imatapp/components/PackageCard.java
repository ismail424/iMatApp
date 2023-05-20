package com.imatapp.components;

import java.util.concurrent.atomic.AtomicInteger;

import com.imatapp.pages.Packages;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class PackageCard {
    private PackageItem[] displayItems;
    private VBox card;
    private Button addToCart;
    private Text totalPriceSumText, cardTitle, itemAmount;
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public PackageCard(VBox card, String title, int price, Product[] displayItemsData) {
        this.card = card;

        cardTitle = (Text) card.lookup(".package_card_title");
        totalPriceSumText = (Text) card.lookup(".package_card_totalprice_sum");
        addToCart = (Button) card.lookup(".package_card_button");
        itemAmount = (Text) card.lookup(".package_card_item_amount");

        cardTitle.setText(title);
        displayItems = new PackageItem[displayItemsData.length];

        int i = 0;
        AtomicInteger index = new AtomicInteger(i);
        card.lookupAll(".package_card_item").forEach(item -> {
            int currentIndex = index.getAndIncrement();
            displayItems[currentIndex] = new PackageItem((HBox) item, displayItemsData[currentIndex]);
        });

        itemAmount.setText("+"+String.valueOf(displayItems.length) + " Varor Till");
        totalPriceSumText.setText(String.valueOf(price) + " kr");
    }



    public class PackageItem {

        private Text itemTitle, itemprice, itemUnit, itemPriceDecimal;
        private ImageView itemImage;

        public PackageItem(HBox item, Product data) {
            itemTitle = (Text) item.lookup(".package_card_item_title");
            itemprice = (Text) item.lookup(".package_card_item_price");
            itemUnit = (Text) item.lookup(".package_card_item_unit");
            itemImage = (ImageView) item.lookup(".package_card_item_image");
            itemPriceDecimal = (Text) item.lookup(".package_card_item_price_decimal");

            itemTitle.setText(data.getName());
            itemImage.setImage(iMatDataHandler.getFXImage( data ) );
            itemprice.setText(String.valueOf(
                (int) data.getPrice()
            ));
            String floatString = String.valueOf(data.getPrice());
            int decimalIndex = floatString.indexOf('.') + 1; 
            String decimalPart = floatString.substring(decimalIndex);
            if (decimalPart.length() == 1) {
                decimalPart += "0";
            }
            if (decimalPart.length() > 2) {
                decimalPart = decimalPart.substring(0, 2);
            } 
            if (decimalPart.equals("00")) {
                decimalPart = "99";
            }
            itemPriceDecimal.setText(decimalPart);
            itemUnit.setText( " " + data.getUnit() );
        }
    }

}
