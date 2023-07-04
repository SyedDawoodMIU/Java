import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

interface BigChainBrand {
    String getName();

    Money getProfit();

    void addChain(BigChainBrand chain);

    List<BigChainBrand> getChains();
}

class Location implements BigChainBrand {
    private String name;
    private List<BigChainBrand> chains;

    public Location(String name) {
        this.name = name;
        this.chains = new ArrayList<>();
    }

    public void addChain(BigChainBrand chain) {
        chains.add(chain);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Money getProfit() {
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (BigChainBrand chain : chains) {
            totalProfit = totalProfit.add(chain.getProfit().getAmount());
        }
        return new Money(totalProfit, "USD");
    }

    public List<BigChainBrand> getChains() {
        return chains;
    }
}

class Store implements BigChainBrand {
    private String name;
    private Money profit;

    public Store(String name, Money profit) {
        this.name = name;
        this.profit = profit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Money getProfit() {
        return profit;
    }

    @Override
    public void addChain(BigChainBrand chain) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addChain'");
    }

    @Override
    public List<BigChainBrand> getChains() {
        return new ArrayList<BigChainBrand>() {
            {
                add(new Store(name, profit));
            }
        };
    };

}

class Money {
    private BigDecimal amount;
    private String currency;

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}

public class App {
    public static void main(String[] args) {

        BigChainBrand EASTCoast = new Location("East Coast");
        BigChainBrand NewYorkChains = new Location("New York");
        BigChainBrand NewJerseyChains = new Location("New Jersey");
        BigChainBrand CaliforniaChains = new Location("California");
        BigChainBrand TexasChains = new Location("Texas");
        BigChainBrand FloridaChains = new Location("Florida");

        BigChainBrand NYCStore = new Store("NYC", new Money(BigDecimal.valueOf(100000), "USD"));
        BigChainBrand NJStore = new Store("NJ", new Money(BigDecimal.valueOf(200000), "USD"));
        BigChainBrand NJStore2 = new Store("NJ2", new Money(BigDecimal.valueOf(400), "USD"));
        BigChainBrand CAStore = new Store("CA", new Money(BigDecimal.valueOf(300000), "USD"));
        BigChainBrand TXStore = new Store("TX", new Money(BigDecimal.valueOf(400000), "USD"));

        NewYorkChains.addChain(NYCStore);
        NewYorkChains.addChain(NJStore);
        NewYorkChains.addChain(NJStore2);

        NewJerseyChains.addChain(NJStore);
        NewJerseyChains.addChain(NJStore2);

        CaliforniaChains.addChain(CAStore);

        TexasChains.addChain(TXStore);

        FloridaChains.addChain(new Store("FL", new Money(BigDecimal.valueOf(500000), "USD")));


        EASTCoast.addChain(NewYorkChains);  
        EASTCoast.addChain(NewJerseyChains);
        EASTCoast.addChain(CaliforniaChains);
        EASTCoast.addChain(TexasChains);
        EASTCoast.addChain(FloridaChains);


        System.out.println("Total profit of East Coast: " + EASTCoast.getProfit().getAmount());

        System.out.println("Total profit of New York: " + NewYorkChains.getProfit().getAmount());
        System.out.println("Total profit of New Jersey: " + NewJerseyChains.getProfit().getAmount());
        System.out.println("Total profit of California: " + CaliforniaChains.getProfit().getAmount());
        System.out.println("Total profit of Texas: " + TexasChains.getProfit().getAmount());
        System.out.println("Total profit of Florida: " + FloridaChains.getProfit().getAmount());

    }
}