package app.onbo.root.callbacks;

import app.onbo.api.models.response.v2.Restaurant;

public interface RestaurantItemClickListener {

    void onRestaurantItemClicked(Restaurant restaurant);
}
