package company.purchases.job;

import company.purchases.service.ExchangeRateService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Exchange Rate Job scheduler for updating the database with new quarterly exchange rates.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateJob {

    private final ExchangeRateService exchangeRateService;

    private boolean initialTaskRun = false;

    /**
     * Update rates scheduled job.
     */
    @Scheduled(cron = "0 0 12 1 * ?") // Noon of first day of every month, as api is updated quarterly
    public void updateRates(){
        log.info("Starting exchange rate update job");
        exchangeRateService.updateExchangeRates();
        log.info("Exchange rate update job finished");
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initialTaskRun) {
            updateRates();
            initialTaskRun = true; // Prevents multiple invocations in case of multiple context refreshes
        }
    }
}
