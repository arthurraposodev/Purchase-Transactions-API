package company.purchases.service;

/**
 * The interface Exchange rate service.
 */
public interface ExchangeRateService {
    /**
     * Update exchange rates.
     * Queries the database for the latest record_date available and adds 1. In case there is no data in the database,
     * the date is 10 years ago. This date is then sent to the Treasury API to receive back only new exchange rate
     * entries, if available. The entries are then persisted to the database.
     */
    void updateExchangeRates();
}
