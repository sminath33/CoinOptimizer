package app.util;

import java.math.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class DollarToCoin {
    
    private BigDecimal dollarValue;
    private String convertedMessage;
    private String dollarValuePlainString;
    private String centsValuePlainString;
    private String silverDollars;
    private String halfDollars;
    private String quarters;
    private String dimes;
    private String nickels;
    private String pennies;
    private Integer centsValueInt;
    private Integer centsMod; 
    private JsonNode coinValuesJson;

    
    public DollarToCoin(BigDecimal dollarValue)
    {
        this.dollarValue = dollarValue;
    }
    
    public JsonNode ConvertAmountToCoins()
    {
        prepareVariablesForProcessing();
        
        this.silverDollars = numberOfSilverDollars();
        this.halfDollars = numberOfHalfDollars();
        this.quarters = numberOfQuarters();
        this.dimes = numberOfDimes();
        this.nickels = numberOfNickels();
        this.pennies = numberOfPennies();
       
        convertVariablesToJson();
        
        return this.coinValuesJson;
    }
    
    private void setCentsValuePlainString()
    {
        this.centsValuePlainString = dollarValuePlainString.split("\\.")[1];;
    }
    
    private void setCentsValueInt()
    {
        this.centsValueInt = Integer.parseInt(this.centsValuePlainString);
    }
    
    private String numberOfSilverDollars()
    {
        String numberOfSilverDollars = dollarValuePlainString.split("\\.")[0];
        
        return numberOfSilverDollars; 
    }
    
    private String numberOfHalfDollars()
    {
        String numberOfHalfDollars;
        
        if( this.centsValueInt >= 50 )
        {
            numberOfHalfDollars = "1";
        }
        else
        {
            numberOfHalfDollars = "0";
        }
        return numberOfHalfDollars;
    }
    
    private String numberOfQuarters()
    {
        String numberOfQuarters = "0";
        
        if ( this.centsMod >= 25 )
        {
            return numberOfQuarters = "1";
        }
        return numberOfQuarters;
    }
    
    private String numberOfDimes()
    {
        Integer dimesMod;
        String numberOfDimes = "0";
        
        dimesMod = centsMod % 25;
        
        if ( dimesMod >= 20 )
        {
            numberOfDimes = "2";
        }
        else if ( dimesMod >= 10)
        {
            numberOfDimes = "1"; 
        }
        return numberOfDimes;
    }
    
    private String numberOfNickels()
    {
        Integer nickelsMod;
        String numberOfNickels = "0";
        
        nickelsMod = centsMod % 10;
        
        if ( centsValueInt < 25 && ( nickelsMod >= 5 && nickelsMod <= 9 ) ) 
        {
            numberOfNickels = "1";
        }
        else if ( ( centsValueInt >= 30 && centsValueInt < 50 ) && ( nickelsMod >= 0 && nickelsMod <= 4 ) )
        {
            numberOfNickels = "1";
        }
        else if ( ( centsValueInt > 50 && centsValueInt < 75 ) && ( nickelsMod >= 5 && nickelsMod <= 9 ) )
        {
            numberOfNickels = "1";
        }
        else if ( centsValueInt > 75 && ( nickelsMod >= 0 && nickelsMod <= 4 ) )
        {
            numberOfNickels = "1";
        }
        
        return numberOfNickels;
    }
    
    private String numberOfPennies()
    {
        Integer pennies;
        String numberOfPennies = "0";
        
        pennies = centsMod % 5;
        
        return String.valueOf(pennies);
    }
    
    private void setCentsMod()
    {
        this.centsMod = this.centsValueInt % 50;
    }
    
    private void setDollarValuePlainString()
    {
        this.dollarValuePlainString = this.dollarValue.toPlainString();
    }
    
    public BigDecimal getDollarValue() {
        return dollarValue;
    }
    
    private void prepareVariablesForProcessing()
    {
        setDollarValuePlainString();
        setCentsValuePlainString();
        setCentsValueInt();
        setCentsMod();
    }
    
    private void convertVariablesToJson()
    {
        CoinValues coinValues = new CoinValues();
        coinValues.setSilverDollar(this.silverDollars);
        coinValues.setHalfDollar(this.halfDollars);
        coinValues.setQuarter(this.quarters);
        coinValues.setDime(this.dimes);
        coinValues.setNickel(this.nickels);
        coinValues.setPenny(this.pennies);
        
        this.coinValuesJson = Json.toJson(coinValues);
    }
    
}