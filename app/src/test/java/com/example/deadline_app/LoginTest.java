//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

//import static org.junit.Assert.*;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
/*
       LoginTest
       The LoginTest will test the login part of the application
       Empty and non-empty input will be tested

*/

public class LoginTest {
String phone, password;


    @Test
    public void emptyPhoneEmptyPw(){
        phone = ""; password = "";
        boolean result = validateFields();
        assertThat(result).isFalse();
    }
    @Test
    public void nonemptyPhoneEmptyPw(){
        phone = "test"; password = "";
        boolean result = validateFields();
        assertThat(result).isFalse();
    }
    @Test
    public void emptyPhoneNonemptyPw(){
        phone = ""; password = "test";
        boolean result = validateFields();
        assertThat(result).isFalse();
    }
    @Test
    public void nonemptyPhoneNonemptyPw(){
        phone = "test"; password = "test";
        boolean result = validateFields();
        assertThat(result).isTrue();
    }
    public boolean validateFields() {
        String _phone = phone.trim();
        String _password = password.trim();
        if (_phone.isEmpty() && _password.isEmpty()) {
            return false;
        }else if ( _password.isEmpty()) {
            return false;
        } else if(_phone.isEmpty()) {
            return false;
        }else{
            return true;
        }

    }

}