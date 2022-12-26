package br.com.six2six.fixturefactory.model;

import java.util.List;
import java.util.stream.Collectors;

public class LambdaPlane {

    private String tailNumber;
    private List<String> flightCrew;

    public LambdaPlane(String tailNumber, List<String> flightCrew) {
        this.tailNumber = tailNumber;
        this.flightCrew = flightCrew;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public List<String> getFlightCrew() {
        return flightCrew;
    }

    @Override
    public String toString() {
        return "LambdaPlane{" +
            "tailNumber='" + tailNumber + '\'' +
            ", flightCrew=" +
            flightCrew.stream()
                .map(p -> "Emp: " + p)
                .sorted()
                .collect(Collectors.joining(", "))
            +
            '}';
    }
}
