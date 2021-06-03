package ae.etisalat.swyp.repository;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;

import ae.etisalat.swyp.model.Transaction_Logger;

public interface TxnLoggerRepository extends CrudRepository<Transaction_Logger, BigDecimal> {

}
