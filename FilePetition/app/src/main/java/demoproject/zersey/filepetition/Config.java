package demoproject.zersey.filepetition;

/**
 * Created by anirudh on 11/4/16.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://192.168.113.5/filepetition/addpetition.php";
    public static final String URL_GET_ALL = "http://192.168.113.5/filepetition/getAllPetition.php";
    //public static final String URL_GET_EMP = "http://172.16.83.60/androiddb/getEmp.php?id=";
    //public static final String URL_UPDATE_EMP = "http://172.16.83.60/androiddb/updateEmp.php";
    //public static final String URL_DELETE_EMP = "http://172.16.83.60/androiddb/deleteEmp.php?id=";

    public static final String URL_USER_ADD="http://192.168.113.5/filepetition/signupuser.php";
    public static final String URL_USER_LOGIN="http://192.168.113.5/filepetition/loginuser.php";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_P_ID = "id";
    public static final String KEY_P_TITLE = "title";
    public static final String KEY_P_DECISION_MAKER = "decision_maker";
    public static final String KEY_P_EXPLANATION = "explanation";
    public static final String KEY_P_CREATEDBY = "createdBy";

    public static final String KEY_U_FIRSTNAME = "firstname";
    public static final String KEY_U_LASTNAME = "lastname";
    public static final String KEY_U_EMAIL = "email";
    public static final String KEY_U_PASSWORD = "encrypted_password";
    public static final String KEY_U_USERNAME = "username";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_DECISION_MAKER = "decision_maker";
    public static final String TAG_EXPLANATION = "explanation";
    public static final String TAG_U_CREATEDBY = "createdBy";

    //employee id to pass with intent
    public static final String petition_ID = "p_id";

    public boolean user_sign = false;
}
