//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;


import static com.google.common.truth.Truth.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateAcTest {
String _username, _phone, _password;

    /*
           Create Account Test
           The CreateAc test will test the logic of create new account validation method
           Empty, non-empty, over-length input will be tested

    */
    private void testModel(int mode){
        switch(mode){
            case 0:                                                        //username and password empty
                _phone = "test"; _username = ""; _password = "";
                break;
            case 1:                                                        //phone and password empty
                _phone = ""; _username = "test"; _password = "";
                break;
            case 2:                                                        //phone and username empty
                _phone = ""; _username = ""; _password = "test";
                break;
            case 3:                                                        //password empty
                _phone = "test"; _username = "test"; _password = "";
                break;
            case 4:                                                        //phone empty
                _phone = ""; _username = "test"; _password = "test";
                break;
            case 5:                                                        //username empty
                _phone = "test"; _username = ""; _password = "test";
                break;
            case 6:                                                        //no empty
                _phone = "test"; _username = "test"; _password = "test";
                break;
            case 7:                                                        //username too long
                _phone = "test"; _username = "123456789012345"; _password = "test";
                break;
            default:                                                       //all fields are empty
                _phone = ""; _username = ""; _password = "";
                break;
        }
    }

    @Before
    public void setUp(){
        _username = "";
        _phone = "";
        _password = "";
    }

    @After
    public void clear(){
        _username = null;
        _phone = null;
        _password = null;
    }

    @Test
    public void all_empty_return_false(){
        testModel(-1);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }

    @Test
    public void username_and_password_empty_return_false(){
        testModel(0);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }

    @Test
    public void phone_and_password_empty_return_false(){
        testModel(1);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }

    @Test
    public void phone_and_username_empty_return_false(){
        testModel(2);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }

    @Test
    public void password_empty_return_false(){
        testModel(3);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }

    @Test
    public void phone_empty_return_false(){
        testModel(4);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }
    @Test
    public void username_empty_return_false(){
        testModel(5);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }
    @Test
    public void no_empty_return_true(){
        testModel(6);
        boolean result = validateFields();
        assertThat(result).isTrue();
    }
    @Test
    public void username_too_long_return_false(){
        testModel(7);
        boolean result = validateFields();
        assertThat(result).isFalse();
    }

    private boolean validateFields() {
        if (_phone.isEmpty()) {
            return false;
        } else if (_username.isEmpty()) {
            return false;
        } else if (_username.length()>=15) {
            return false;
        } else if (_password.isEmpty()) {
            return false;
        } else {
            return true;

        }

    }

    private void addTask(){

    }

}