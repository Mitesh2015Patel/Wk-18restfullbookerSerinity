package com.restful.booker.bookinginfo;

import com.restful.booker.model.BookingDatePojo;
import com.restful.booker.testbase.TestBaseBooking;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCURDTestWithSteps extends TestBaseBooking
{
    static String  firstname = "Jimmy" + TestUtils.getRandomName();
    static String lastname = "patel"+ TestUtils.getRandomName();
    static Integer totalprice = 111 ;
    static Boolean depositpaid = true;
    static Date checkin = new Date(122,01,21);
    static Date checkout = new Date(122,01,24);
    static String additionalneeds = "Money";
    static int bookingId;

    @Steps
    BookingSteps bookingSteps;


    @Test
    public void test001()
    {
        BookingDatePojo bookingDatePojo = new BookingDatePojo();
        bookingDatePojo.setCheckin(checkin);
        bookingDatePojo.setCheckout(checkout);
        ValidatableResponse response = bookingSteps.createBooking(firstname,lastname,totalprice,depositpaid,bookingDatePojo,additionalneeds);
        response.log().all().statusCode(200);
    }

    @Title("Verify if the Booking was done correctly")
    @Test
    public void test002() {
        ArrayList<HashMap<String, Object>> value = bookingSteps.getBookingInfoByFirstname(firstname);
        Assert.assertThat(value.get(0), hasValue(firstname));
        bookingId = (Integer) value.get(0).get("bookingid");
        System.out.println(bookingId);
    }



    @Title("Update the Booking and verify the updated information")
    @Test
    public void test003() {

        additionalneeds = "Food";

        BookingDatePojo bookingdates = new BookingDatePojo();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        bookingSteps.updateBooking(bookingId, firstname, lastname, totalprice, depositpaid,  bookingdates, additionalneeds).log().all().statusCode(200);

    }



    @Title("Delete the Booking")
    @Test
    public void test004() {
        bookingSteps.deleteBooking(bookingId).statusCode(201);
        bookingSteps.getBookingById(bookingId).statusCode(404);
    }


    @Title("Get all Booing Info")
    @Test
    public void Test004()
    {
        bookingSteps.getAllBooking().log().all().statusCode(200);
    }


    @Title("Get all Booing Info")
    @Test
    public void Test005()
    {
        bookingSteps.getAllBooking().log().all().statusCode(200);
    }


    @Title("Get all Booing Info")
    @Test
    public void Test006()
    {
        bookingSteps.getAllBookingByDate().log().all().statusCode(200);
    }

}
