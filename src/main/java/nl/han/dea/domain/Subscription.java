package nl.han.dea.domain;

public class Subscription {

    private int id;
    private String aanbieder;
    private String dienst;
    private int prijs;
    private boolean deelbaar;
    private int deelbaar_aantal;
    private boolean verdubbeling;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public void setAanbieder(String aanbieder) {
        this.aanbieder = aanbieder;
    }

    public String getDienst() {
        return dienst;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public boolean isDeelbaar() {
        return deelbaar;
    }

    public void setDeelbaar(boolean deelbaar) {
        this.deelbaar = deelbaar;
    }

    public int getDeelbaar_aantal() {
        return deelbaar_aantal;
    }

    public void setDeelbaar_aantal(int deelbaar_aantal) {
        this.deelbaar_aantal = deelbaar_aantal;
    }


    public boolean isVerdubbeling() {
        return verdubbeling;
    }

    public void setVerdubbeling(boolean verdubbeling) {
        this.verdubbeling = verdubbeling;
    }
}
