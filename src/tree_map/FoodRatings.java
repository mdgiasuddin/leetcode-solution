package tree_map;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// Leetcode problem: 2353
/*
 * Design a Food Rating System.
 * */
public class FoodRatings {
    Map<String, FoodRating> ratingMap;
    Map<String, TreeSet<FoodRating>> cuisineMap;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        this.ratingMap = new HashMap<>();
        this.cuisineMap = new HashMap<>();

        int n = ratings.length;

        for (int i = 0; i < n; i++) {
            FoodRating foodRating = new FoodRating(foods[i], cuisines[i], ratings[i]);
            ratingMap.put(foods[i], foodRating);

            TreeSet<FoodRating> foodRatingSet = cuisineMap.getOrDefault(cuisines[i], new TreeSet<>());
            foodRatingSet.add(foodRating);
            cuisineMap.put(cuisines[i], foodRatingSet);
        }
    }

    public void changeRating(String food, int newRating) {
        FoodRating foodRating = ratingMap.get(food);
        ratingMap.remove(food);
        FoodRating newFoodRating = new FoodRating(food, foodRating.getCuisine(), newRating);
        ratingMap.put(food, newFoodRating);
        cuisineMap.get(foodRating.getCuisine()).remove(foodRating);
        cuisineMap.get(foodRating.getCuisine()).add(newFoodRating);
    }

    public String highestRated(String cuisine) {
        TreeSet<FoodRating> foodRatingSet = cuisineMap.get(cuisine);
        return foodRatingSet.first().getFood();
    }
}

class FoodRating implements Comparable<FoodRating> {
    private String food;
    private String cuisine;
    private int rating;

    public FoodRating(String food, String cuisine, int rating) {
        this.food = food;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(FoodRating o) {
        return this.rating == o.rating ? this.food.compareTo(o.food) : o.rating - this.rating;
    }
}
