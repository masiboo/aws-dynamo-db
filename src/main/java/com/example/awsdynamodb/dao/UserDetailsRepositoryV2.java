package com.example.awsdynamodb.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.awsdynamodb.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

/**
 * @author tapas
 *
 */
@Repository
public class UserDetailsRepositoryV2 {

    private static Logger LOGGER = LoggerFactory.getLogger(UserDetailsRepositoryV2.class);

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDb = new DynamoDB(client);

    public UserDetails getUserDetails(String key) {
        Table table = dynamoDb.getTable("user");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_id", key);
        try {
            System.out.println("Attempting to read item");
            Item outcome = table.getItem(spec);
            if (Objects.nonNull(outcome)) {
                UserDetails user = new UserDetails();
                user.setUserId(outcome.get("user_id").toString());
                user.setFirstName(outcome.get("first_name").toString());
                user.setLastName(outcome.get("last_name").toString());
                return user;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred during getUserDetails : ", e);
        }
        return null;
    }

    public String addUserDetails(UserDetails user) {
        Table table = dynamoDb.getTable("user");
        try {
            final Map<String, String> addressMap = new HashMap<String, String>();
            addressMap.put("street", "Espoontie 7");
            addressMap.put("post-code", "02760");
            addressMap.put("post", "espoo");
            final Map<String, String> productMap = new HashMap<String, String>();
            productMap.put("name", "shoe");
            productMap.put("size", "9");
            productMap.put("price", "202.50");
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey("user_id", user.getUserId()).with("first_name", user.getFirstName())
                            .with("last_name", user.getLastName()).withMap("address", addressMap).withMap("Product", productMap));
            if (Objects.nonNull(outcome))
                return "SUCCESS";
            else
                return "FAILURE";
        } catch (Exception e) {
            LOGGER.error("Exception occurred while adding record to the db : ", e);
            return null;
        }
    }

}
