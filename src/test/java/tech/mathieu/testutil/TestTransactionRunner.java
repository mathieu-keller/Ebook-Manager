package tech.mathieu.testutil;

import java.util.concurrent.Callable;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class TestTransactionRunner {
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public <T> T runInNewTransaction(Callable<T> func) throws Exception {
    return func.call();
  }
}
