package com.stratio.barclays.obproducer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.stratio.barclays.obproducer.application.datageneration.generator.RandomLocalDateTime;
import com.stratio.barclays.obproducer.infrastructure.config.spring.ObConfig.ObPopulationConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ob_transaction_data")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@ToString
public class ObTransactionData {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long transactionId;

  @JsonProperty("TransactionAmount")
  @PodamExclude
  private BigDecimal transactionAmount;

  @JsonProperty("SourceCurrency")
  @PodamExclude
  private String transactionSourceCurrency = Stream.of("GBP", "EUR", "USD")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("TargetCurrency")
  @PodamExclude
  private String transactionTargetCurrency = Stream.of("GBP", "EUR", "USD")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("UnitCurrency")
  @PodamExclude
  private String transactionUnitCurrency = Stream.of("GBP", "EUR", "USD")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("ExchangeRate")
  @Digits(integer = 1, fraction = 3)
  private String transactionExchangeRate;

  @JsonProperty("QuotationDate")
  @PodamStrategyValue(RandomLocalDateTime.class)
  private String transactionQuotationDate;

  @JsonProperty("SubCode")
  private String transactionSubCode;

  @JsonProperty("Issuer")
  @Size(min = 1, max = 35)
  private String transactionIssuer;

  @JsonProperty("transactionType")
  @PodamExclude
  private String transactionType = "ClosingBooked";

  @JsonProperty("MerchantName")
  @Size(min = 1, max = 35)
  private String transactionMerchantName;

  @JsonProperty("CardSchemeName")
  private String transactionSchemeName;

  @JsonProperty("ContractIdentification")
  private String transactionContractIdentification;

  @JsonProperty("AddressLine")
  @Size(min = 1, max = 70)
  private String transactionAddressLine;

  @JsonProperty("AuthorisationType")
  @PodamExclude
  private String transactionAuthorisationType = "PIN";

  @JsonProperty("CardPanNumber")
  @PodamExclude
  private String transactionCardPanNumber;

  @JsonProperty("BookingDateTime")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @PodamExclude
  private LocalDateTime transactionBookingDateTime;

  @JsonProperty("CreditDebitIndicator")
  @PodamExclude
  private String balancesCreditDebitIndicator = Stream.of("Credit", "Debit")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("BalancesType")
  @PodamExclude
  private String balancesType = Stream.of("ClosingBooked", "ClosingAvailable")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("ClosingDateTime")
  @PodamStrategyValue(RandomLocalDateTime.class)
  private String balancesDateTime;

  @JsonProperty("BalanceAmount")
  @Digits(integer = 5, fraction = 2)
  private String balanceAmount;

  @JsonProperty("BalanceCurrency")
  @PodamExclude
  private String balancesCurrency = Stream.of("GBP", "EUR", "USD")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("MerchantCategoryCode")
  @PodamExclude
  private String mccId = Stream.of("5655", "5941", "7032", "7941", "7997", "8888", "5555")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("Code")
  private String transactionCodeId;

  @JsonProperty("AccountId")
  private String accountId;

  @JsonProperty("AccountStatus")
  @PodamExclude
  private String accountStatus = Stream.of("Enabled", "Disabled")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("AccountStatusUpdateDateTime")
  @PodamStrategyValue(RandomLocalDateTime.class)
  private String accountStatusUpdateDateTime;

  @JsonProperty("AccountCurrency")
  private String accountCurrency = Stream.of("GBP", "EUR", "USD")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("AccountType")
  @PodamExclude
  private String accountType = Stream.of("Personal", "Business")
      .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
      .findAny()
      .get();

  @JsonProperty("AccountDescription")
  @Size(min = 1, max = 35)
  private String accountDescription;

  @JsonProperty("AccountName")
  @Size(min = 1, max = 70)
  private String accountName;

  @JsonIgnore
  @PodamExclude
  private Boolean sent = false;

  @JsonIgnore
  @Transient
  @PodamExclude
  private ObPopulationConfig obPopulationConfig;

  public void setObPopulation(ObPopulationConfig obPopulationConfig) {
    this.obPopulationConfig = obPopulationConfig;
  }
}
