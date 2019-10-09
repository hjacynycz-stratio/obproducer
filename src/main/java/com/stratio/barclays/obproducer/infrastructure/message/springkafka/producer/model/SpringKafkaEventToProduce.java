package com.stratio.barclays.obproducer.infrastructure.message.springkafka.producer.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SpringKafkaEventToProduce implements Serializable {

  @Id
  @GeneratedValue
  private Long transactionId;

  @JsonProperty("TransactionAmount")
  private String transactionsAmount;

  @JsonProperty("SourceCurrency")
  private String transactionsSourceCurrency;

  @JsonProperty("TargetCurrency")
  private String transactionsTargetCurrency;

  @JsonProperty("UnitCurrency")
  private String transactionsUnitCurrency;

  @JsonProperty("ExchangeRate")
  private String transactionsExchangeRate;

  @JsonProperty("QuotationDate")
  private String transactionsQuotationDate;

  @JsonProperty("SubCode")
  private String transactionsSubCode;

  @JsonProperty("Issuer")
  private String transactionsIssuer;

  @JsonProperty("TransactionsType")
  private String transactionsType;

  @JsonProperty("MerchantName")
  private String transactionsMerchantName;

  @JsonProperty("CardSchemeName")
  private String transactionsSchemeName;

  @JsonProperty("ContractIdentification")
  private String transactionsContractIdentification;

  @JsonProperty("AddressLine")
  private String transactionsAddressLine;

  @JsonProperty("AuthorisationType")
  private String transactionsAuthorisationType;

  @JsonProperty("CreditDebitIndicator")
  private String balancesCreditDebitIndicator;

  @JsonProperty("BalancesType")
  private String balancesType;

  @JsonProperty("Closing_DateTime")
  private String balancesDateTime;

  @JsonProperty("BalancesAmount")
  private String balancesAmount;

  @JsonProperty("BalancesCurrency")
  private String balancesCurrency;

  @JsonProperty("MerchantCategoryCode")
  private String mccId;

  @JsonProperty("Code")
  private String transactionCodeId;

  @JsonProperty("AccountId")
  private String accountId;

  @JsonProperty("AccountStatus")
  private String accountStatus;

  @JsonProperty("AccountStatusUpdateDateTime")
  private String accountsStatusUpdateDateTime;

  @JsonProperty("AccountCurrency")
  private String accountsCurrency;

  @JsonProperty("AccountType")
  private String accountsAccountType;

  @JsonProperty("AccountDescription")
  private String accountsDescription;

  @JsonProperty("AccountName")
  private String accountsName;

}
