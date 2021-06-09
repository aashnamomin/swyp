package ae.etisalat.swyp.repository;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ae.etisalat.swyp.model.Transaction_Logger;

@Repository
public interface TxnLoggerRepository extends CrudRepository<Transaction_Logger, BigDecimal> {

}
