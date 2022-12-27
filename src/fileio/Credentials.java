package fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private Integer balance = 0;

    public Credentials() {

    }

    @JsonIgnore
    public Credentials(final Credentials credentials) {
        this.name = credentials.name;
        this.password = credentials.password;
        this.accountType = credentials.accountType;
        this.country = credentials.country;
        this.balance = credentials.balance;
    }

    /**
     * convert balance from int to String
     * @return
     */
    @JsonProperty("balance")
    public String balanceToString() {
        return balance.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Integer getBalance() {
        return balance;
    }

    @JsonProperty("balance")
    public void setBalance(final int balance) {
        this.balance = balance;
    }
}
