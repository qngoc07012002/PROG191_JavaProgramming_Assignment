package Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class CustomerControllerTest {

    @Test
    public void testCheckValidEmail() {
        CustomerController controller = new CustomerController();
        assertTrue(controller.checkValidEmail("ngoc@gmail.com"));
        assertTrue(controller.checkValidEmail("ngoc@fpt.edu.vn"));
    }

    @Test
    public void testCheckInValidEmail() {
        CustomerController controller = new CustomerController();
        assertFalse(controller.checkValidEmail("ngoc@gmail"));
        assertFalse(controller.checkValidEmail("ngoc.com"));
    }


    @Test
    public void testCheckValidPhoneNumber() {
        CustomerController controller = new CustomerController();
        assertTrue(controller.checkValidPhoneNumber("+1234567890"));
        assertTrue(controller.checkValidPhoneNumber("123-456-7890"));

    }

    @Test
    public void testCheckInValidPhoneNumber() {
        CustomerController controller = new CustomerController();
        assertFalse(controller.checkValidPhoneNumber("+123456"));
        assertFalse(controller.checkValidPhoneNumber("abc"));
    }

    @Test
    public void testCheckValidAge() {
        CustomerController controller = new CustomerController();
        assertTrue(controller.checkValidAge("18"));
        assertTrue(controller.checkValidAge("30"));
        assertTrue(controller.checkValidAge("50"));
    }

    @Test
    public void testCheckInValidAge() {
        CustomerController controller = new CustomerController();
        assertFalse(controller.checkValidAge("0"));
        assertFalse(controller.checkValidAge("abc"));
    }
}