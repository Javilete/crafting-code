package crafting.code.bankkata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalTransactionRepositoryShould {

    @Mock
    private Clock clock;

    private TransactionRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new LocalTransactionRepository(clock);
        when(clock.now()).thenReturn("18/09/2017");
    }

    @Test
    public void create_a_deposit() {
        repository.createDeposit(100);

        assertThat(repository.allTransactions().size(), is(1));
        assertThat(repository.allTransactions().get(0).getAmount(), is(100));
        assertThat(repository.allTransactions().get(0).getDate(), is("18/09/2017"));
    }

    @Test
    public void create_a_withdraw() {
        repository.createWithdraw(100);

        assertThat(repository.allTransactions().size(), is(1));
        assertThat(repository.allTransactions().get(0).getAmount(), is(-100));
        assertThat(repository.allTransactions().get(0).getDate(), is("18/09/2017"));
    }
}
