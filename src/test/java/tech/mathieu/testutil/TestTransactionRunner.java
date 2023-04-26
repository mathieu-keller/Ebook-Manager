package tech.mathieu.testutil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.concurrent.Callable;

@ApplicationScoped
public class TestTransactionRunner {
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public <T> T runInNewTransaction(Callable<T> func) throws Exception {
    return func.call();
  }
}
