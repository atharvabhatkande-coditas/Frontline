package com.coditas.frontline.constants;

public final class AuthConstants {
    private AuthConstants(){

    }
    public static final String EMPLOYEE="employee";
    public static final String CUSTOMER="customer";

    public static final String UNAUTHORIZED="Unauthorized";
    public static final String USER="User";
    public static final String BAD_CREDENTIALS="Bad Credentials";
    public static final String VERIFY_CODE="Please verify your email";
    public static final String REGISTRATION_SUCCESS="Registration Successful";
    public static final String LOGIN_AGAIN="Session Expired. Please login again";
    public static final String LOGOUT_SUCCESSFUL="Logged Out successfully";



    public static final String EMAIL_TEXT= """
            Your Invitation Link: %s
            
            Your Invitation Code: %s
            """;
    public static final String INVITATION_LINK="https://coming-revivable-scandal.ngrok-free.dev/frontline/v1/auth/register";
    public static final String EMAIL_SENT="Invitation Sent Successfully";

    public static final String PROMPT="You are a customer support agents copilot which helps the support agent fetch customer related data";



}
