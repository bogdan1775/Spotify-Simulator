package app.audio.Files;

import fileio.input.CommandInput;

public final class Event {
    private String name;
    private String owner;
    private String description;
    private String date;

    private static final Integer DAY = 31;
    private static final Integer MONTH = 12;
    private static final Integer YEAR_MAX = 2023;
    private static final Integer YEAR_MIN = 1900;
    private static final Integer FEBRUARY = 2;
    private static final Integer FEBRUARY_DAYS = 28;
    private static final Integer INDEXDAY = 2;
    private static final Integer INDEXMONTH = 5;
    private static final Integer INDEXYEAR = 10;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * Verifica daca data este valida
     *
     * @param commandInput
     * @return
     */
    public static String verificaData(final CommandInput commandInput) {
        String message = null;
        String dayS = commandInput.getDate().substring(0, 2);
        int day = Integer.parseInt(dayS);
        String monthS = commandInput.getDate().substring(INDEXDAY + 1, INDEXMONTH);
        int month = Integer.parseInt(monthS);
        String yearS = commandInput.getDate().substring(INDEXMONTH + 1, INDEXYEAR);
        int year = Integer.parseInt(yearS);

        if (day > DAY) {
            message = "Event for " + commandInput.getUsername() + " does not have a valid date.";
        } else if (month > MONTH) {
            message = "Event for " + commandInput.getUsername() + " does not have a valid date.";
        } else if (month == FEBRUARY && day > FEBRUARY_DAYS) {
            message = "Event for " + commandInput.getUsername() + " does not have a valid date.";
        } else if (year < YEAR_MIN || year > YEAR_MAX) {
            message = "Event for " + commandInput.getUsername() + " does not have a valid date.";
        }
        return message;
    }

}
