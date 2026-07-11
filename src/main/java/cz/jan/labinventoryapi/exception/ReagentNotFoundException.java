package cz.jan.labinventoryapi.exception;

public class ReagentNotFoundException extends RuntimeException {
    public ReagentNotFoundException(Long id) {
        super("Reagent with id " + id + " not found");
    }
}