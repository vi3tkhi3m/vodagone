package datasource.util;

public class NamedQueries {
    // Subscribers
    public static final String GET_ALL_SUBSCRIBERS = "SELECT * FROM abonnees";
    public static final String SHARE_SUBSCRIPTION_WITH_SUBSCRIBER = "INSERT INTO abonnees_abonnementen VALUES (NULL, ?, ?)";

    // Subscription
    public static final String INSERT_INTO_SUBSCRIPTIONS = "INSERT INTO abonnementen (aanbieder, dienst, prijs) VALUES (?,?,?)";
    public static final String GET_ALl_SUBSCRIPTIONS= "SELECT * FROM abonnementen";
    public static final String GET_SUBSCRIPTION_BY_ID= "SELECT * FROM abonnementen WHERE id = ?";
    public static final String GET_SUBSCRIPTION_PRICE = "SELECT prijs FROM abonnementen WHERE id = ?";
    public static final String GET_ALL_SUBSCRIPTIONS_FOR_USER = "SELECT * FROM abonnementen WHERE id IN (SELECT ga.abonnement_id FROM gebruikers_abonnementen ga INNER JOIN gebruikers g ON ga.gebruikers_id = g.id WHERE g.token = ?)";

    // UserSubscription
    public static final String GET_ALL_USER_SUBSCRIPTIONS = "SELECT * FROM gebruikers_abonnementen";
    public static final String CREATE_USER_SUBSCRIPTION = "INSERT INTO gebruikers_abonnementen VALUES (NULL,?,?,?,?,?,?, ?);";
    public static final String GET_USER_SUBSCRIPTIONS_BY_SUB_USR_ID = "SELECT * FROM gebruikers_abonnementen WHERE abonnement_id = ? AND gebruikers_id = ?";
    public static final String GET_ALL_USER_SUBSCRIPTIONS_OF_USER= "SELECT * FROM gebruikers_abonnementen WHERE gebruikers_id IN (SELECT id FROM gebruikers WHERE id = ?)";
    public static final String TERMINATE_USER_SUBSCRIPTION = "UPDATE gebruikers_abonnementen SET status = \"opgezegd\" WHERE gebruikers_id = ? AND abonnement_id = ?";
    public static final String UPGRADE_USER_SUBSCRIPTION = "UPDATE gebruikers_abonnementen SET verdubbeling = \"verdubbeld\", prijs = ? WHERE gebruikers_id = ? AND abonnement_id = ?";

    // Login
    public static final String FIND_USER_BY_USER_AND_PASSWORD = "SELECT 1 FROM gebruikers WHERE user = ? AND password = ?";
    public static final String ADD_TOKEN_TO_USER = "UPDATE gebruikers SET token = ? WHERE user = ?";
    public static final String GET_TOKEN_FROM_USER = "SELECT token FROM gebruikers WHERE user = ?";
    public static final String GET_ID_FROM_USER_WITH_TOKEN = "SELECT id FROM gebruikers WHERE token = ?";
    public static final String FIND_TOKEN = "SELECT token FROM gebruikers WHERE token = ?";

}