package app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class FilterDate {

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate initialDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate finishDate;

    public FilterDate() {
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }
}
