import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void beforeEachTest(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime twoHoursAfterOpeningTime = spyRestaurant.openingTime.plusHours(2);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(twoHoursAfterOpeningTime);
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime twoHoursAfterClosingTime = spyRestaurant.closingTime.plusHours(2);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(twoHoursAfterClosingTime);
        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /*
    Feature to be developed using the TDD approach for getting the total order
    amount from the restaurant menu items.
    */

    @Test
    public void return_total_amount_for_all_menu_items() throws restaurantNotFoundException{

        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Hyderabad",openingTime,closingTime);
        restaurant.addToMenu("Irani Chai", 20);
        restaurant.addToMenu("Chole Bhature", 40);
        restaurant.addToMenu("Idli", 20);
        restaurant.addToMenu("Dosa", 30);
//        assertNotNull(restaurant.getMenu());

        List<Item> menuItems = restaurant.getMenu();

        assertThat(110, equalTo(restaurant.totalOrderAmount(menuItems)));
    }

    @Test
    public void selecting_zero_items_should_return_totalAmount_as_zero(){
//        LocalTime openingTime = LocalTime.parse("10:30:00");
//        LocalTime closingTime = LocalTime.parse("22:00:00");
//        restaurant = new Restaurant("Amelie's cafe","Hyderabad",openingTime,closingTime);

        List<Item> menuItems = restaurant.getMenu();
        menuItems.clear();
        assertThat(menuItems, hasSize(0));

        assertEquals(0, restaurant.totalOrderAmount(menuItems));
    }

    @Test
    public void selecting_random_items_should_return_totalAmount_of_selected_items_only(){
//        LocalTime openingTime = LocalTime.parse("10:30:00");
//        LocalTime closingTime = LocalTime.parse("22:00:00");
//        restaurant = new Restaurant("Amelie's cafe","Hyderabad",openingTime,closingTime);
        restaurant.addToMenu("Irani Chai", 20);
        restaurant.addToMenu("Chole Bhature", 40);
        restaurant.addToMenu("Idli", 20);
        restaurant.addToMenu("Dosa", 30);

        int menuSize = restaurant.getMenu().size();

        Random random = new Random();
        int randomValue = random.nextInt(menuSize);

        List<Item> randomItems = new ArrayList<Item>();

        Item item = restaurant.getMenu().get(randomValue);
        randomItems.add(item);

        // Test if the random item price is returned by the totalOrderAmount method
        assertThat(item.getPrice(), equalTo(restaurant.totalOrderAmount(randomItems)));
    }
}