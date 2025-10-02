import java.util.*;

public class Customer {
    private final String name;
    private final List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    private double getAmountFor(Rental rental) {
        return rental.getRentalPrice();
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\n");

        for (Rental rental : rentals) {
            frequentRenterPoints++;
            if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1) {
                frequentRenterPoints++;
            }

            double thisAmount = getAmountFor(rental);

            result.append("\t")
                    .append(rental.getMovie().getTitle())
                    .append("\t")
                    .append(thisAmount)
                    .append("\n");

            totalAmount += thisAmount;
        }

        result.append("You owed ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points\n");

        return result.toString();
    }

    public String htmlStatement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder();

        result.append("<H1>Rentals for <EM>").append(getName()).append("</EM></H1><P>\n");

        for (Rental rental : rentals) {
            double thisAmount = getAmountFor(rental);

            frequentRenterPoints += frequentRenterPointsFor(rental);

            result.append(rental.getMovie().getTitle())
                    .append(": ")
                    .append(thisAmount)
                    .append("<BR>\n");

            totalAmount += thisAmount;
        }

        result.append("<P>You owe <EM>").append(totalAmount).append("</EM><P>\n");
        result.append("On this rental you earned <EM>")
                .append(frequentRenterPoints)
                .append("</EM> frequent renter points<P>\n");

        return result.toString();
    }

    private int frequentRenterPointsFor(Rental rental) {
        int points = 1;
        if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1) {
            points++;
        }
        return points;
    }
}
