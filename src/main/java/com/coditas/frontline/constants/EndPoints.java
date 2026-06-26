package com.coditas.frontline.constants;

public final  class EndPoints {
    private EndPoints(){

    }

    public static final String AUTH="/auth/user/**";
    public static final String CUSTOMER_AUTH="/auth/customer/**";

    public static final String INVITATION="/invite/**";

    public static final String SWAGGER1="/swagger-ui/**";
    public static final String SWAGGER2="/v3/api-docs/**";
    public static final String SWAGGER3="/swagger-ui.html/**";


    public static final String TICKET="/ticket/**";

    public static final String TICKET_MANAGER="/ticket/set-priority/**";
    public static final String TICKET_MANAGER_2="/ticket/update-status/**";
    public static final String TICKET_AGENT="/ticket/resolve/**";

    public static final String TICKET_CUSTOMER="/ticket/re-open/**";

    public static final String RATING="/ticket/rating/**";

    public static final String TICKET_ASSIGNMENT="/assign/**";

    public static final String TICKET_RATING="/rating/**";


    public static final String USER="/user/**";


    public static final String COPILOT="/copilot/**";

    public static final String CHAT="/chat/**";


}
