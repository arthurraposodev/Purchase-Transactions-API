package company.purchases.job;

import company.purchases.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateJob {

    private final ExchangeRateService exchangeRateService;

    //@Scheduled(cron = "0 0 12 1 * ?") // Noon of first day of every month, as api is updated quarterly
    @Scheduled(fixedRate = 60000)
    public void updateRates(){
        log.info("Starting exchange rate update job");
        exchangeRateService.updateExchangeRates();
        log.info("Exchange rate update job finished");
    }
}
