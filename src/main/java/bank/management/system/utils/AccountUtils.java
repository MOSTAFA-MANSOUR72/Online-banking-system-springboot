package bank.management.system.utils;

import java.time.Year;

public class AccountUtils {
    public static final String jwt_secret_key = "D97efCY0fmIbtgY6nyY+oAhqZhOLWW8iWjX+9rjBJ90EaxDGBSkHHe/WTPORd0ocDwR6VWxRwj9FCUumCo1eBhWMaOYSm2Vbpkb2FX7fDWg=";
    public static final long expiration_duration = 60000*24;
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_DEPOSIT_SUCCESS_CODE = "005";
    public static final String ACCOUNT_WITHDRAW_SUCCESS_CODE = "006";


    public static final String ACCOUNT_FOUND_MESSAGE = "The user account has been found successfully.";
    public static final String ACCOUNT_DEPOSIT_SUCCESS_MESSAGE = "The deposit is complete successfully.";
    public static final String ACCOUNT_WITHDRAW_SUCCESS_MESSAGE = "The withdraw is complete successfully.";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        int randNumber = (int)( Math.random() *( max-min +1)) +min;


        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        return year + randomNumber;
    }
}
