package app;

public final class EndProgram {

    private double merchRevenue;
    private double songRevenue;
    private int ranking;
    private String mostProfitableSong;

    public double getMerchRevenue() {
        return merchRevenue;
    }

    public void setMerchRevenue(final double merchRevenue) {
        this.merchRevenue = merchRevenue;
    }

    public String getMostProfitableSong() {
        return mostProfitableSong;
    }

    public void setMostProfitableSong(final String mostProfitableSong) {
        this.mostProfitableSong = mostProfitableSong;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(final int ranking) {
        this.ranking = ranking;
    }

    public double getSongRevenue() {
        return songRevenue;
    }

    public void setSongRevenue(final double songRevenue) {
        this.songRevenue = songRevenue;
    }

}
