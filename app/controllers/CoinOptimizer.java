package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.math.BigDecimal;
import java.text.ParsePosition;
import app.util.Money;
import app.util.DollarToCoin;

public class CoinOptimizer extends Controller {
    public Result index(String dollarAmount) {
        JsonNode jsonResponse = null;
        BigDecimal big = null;
        
        try
        {
            big = new BigDecimal(dollarAmount);
        }
        catch (NumberFormatException e)
        {
            return badRequest("Input String: " + dollarAmount + " is not a valid number.");
        }
        
        //converts BigD to dollars
        Money bigToMoney = Money.dollars(big);
        
        //converts BigD to amount
        BigDecimal dollarAmountBigD = bigToMoney.getAmount();
        
        DollarToCoin convertedValue = new DollarToCoin(dollarAmountBigD);
        JsonNode coinValuesJson = convertedValue.ConvertAmountToCoins();
        
        return ok(coinValuesJson);
    }
}
