package com.takeIt.entity;

import javax.persistence.*;

@Entity
public class ExchangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Account account;
    @OneToOne
    private Gift gift;

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Account getAccount() {
        return account;
    }

    public Gift getGift() {
        return gift;
    }

    public static final class ExchangeRequestBuilder {
        private long id;
        private String message;
        private Account account;
        private Gift gift;

        private ExchangeRequestBuilder() {
        }

        public static ExchangeRequestBuilder anExchangeRequest() {
            return new ExchangeRequestBuilder();
        }

        public ExchangeRequestBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ExchangeRequestBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ExchangeRequestBuilder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public ExchangeRequestBuilder setGift(Gift gift) {
            this.gift = gift;
            return this;
        }

        public ExchangeRequest build() {
            ExchangeRequest exchangeRequest = new ExchangeRequest();
            exchangeRequest.id = this.id;
            exchangeRequest.gift = this.gift;
            exchangeRequest.message = this.message;
            exchangeRequest.account = this.account;
            return exchangeRequest;
        }
    }
}
